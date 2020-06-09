package com.bong.bongstagram.Main.Ui.Reply;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bong.bongstagram.Main.Model.ReplyList;
import com.bong.bongstagram.Main.Ui.Main.MainActivity;
import com.bong.bongstagram.R;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class ReplyFragment extends Fragment implements ReplyAdapter.OnItemClickListener {

    private ArrayList<ReplyList> replyLists;
    private List<Integer> selectedItemCount = new ArrayList<>();
    private Button replyBtn;
    private EditText replyEditText = null;
    private String userUUID = null;
    private String replyMessage = null;
    static final int VIEW_TYPE_A = 0;
    static final int VIEW_TYPE_B = 1;
    private ReplyAdapter replyAdapter;
    private RecyclerView recyclerView;
    private String replyId;
    private Toolbar replyToolbar;
    private LinearLayout replyEditTextLayout;
    private TextView toolBarTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reply, container, false);
        ((MainActivity) getActivity()).Toolbar(MainActivity.Type.reply);
        ((MainActivity) getActivity()).bottomNavigation(MainActivity.Type.reply);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.reply_recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);

        replyLists = new ArrayList<>();

        /*---------------------------------------------------------------------*/

        ImageView replyImage = view.findViewById(R.id.reply_Image);
        ImageView profile = view.findViewById(R.id.reply_Url);
        TextView movieTitle, movieContent, movieDate;
        replyBtn = view.findViewById(R.id.reply_Btn);
        toolBarTitle = view.findViewById(R.id.reply_Toolbar_Title);
        replyEditTextLayout = view.findViewById(R.id.reply_EditText_Layout);
        replyToolbar = view.findViewById(R.id.reply_Toolbar);
        replyEditText = view.findViewById(R.id.reply_EditText);

        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String desc = getArguments().getString("desc");
            String image = getArguments().getString("image");

            Glide.with(context)
                    .load(image)
                    .into(profile);
            Glide.with(context)
                    .load(image)
                    .into(replyImage);
            movieTitle = view.findViewById(R.id.reply_Title);
            movieTitle.setText(title);
            movieContent = view.findViewById(R.id.reply_Content);
            movieContent.setText(desc);
            movieDate = view.findViewById(R.id.reply_Date);
            movieDate.setText(period());

            replyEditText.setImeOptions(EditorInfo.IME_ACTION_SEND);
            replyEditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        }

        replyBtn.setEnabled(false);
        replyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() != 0) {
                    replyBtn.setEnabled(true);
                    replyBtn.setTextColor(getResources().getColor(R.color.blue_400));
                } else {
                    replyBtn.setEnabled(false);
                    replyBtn.setTextColor(getResources().getColor(R.color.blue_200));
                }
            }
        });
        appendText(-1);
    }

    @Override
    public void onItemSelected(View v, int position) {
        showKeyBoard();
        replyId = replyLists.get(position).getParentId();
        Log.e("Id", "Id = " + replyId);
        if (replyId != null) {
            appendText(position);
        } else {
            appendText(-1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onLayOutSelected(View v, int position, SparseBooleanArray mSelectedItems, boolean setEnabled) {
        replyAdapter.notifyDataSetChanged();
        if (mSelectedItems.get(position, false)) {
            mSelectedItems.put(position, false);
            if (!mSelectedItems.get(position)) {
                for (int i = 0; i < selectedItemCount.size(); i++) {
                    if (selectedItemCount.get(i) == position) {
                        selectedItemCount.remove(i);
                        countItemsTitle(mSelectedItems);
                    }
                }
            }
            setEnabledBtn();
            v.setBackgroundColor(getContext().getResources().getColor(R.color.colorDefault));
            toolbarToggle();
        } else {
            mSelectedItems.put(position, true);
            selectedItemCount.add(position);
            countItemsTitle(mSelectedItems);
            setEnabledBtn();
            toolbarToggle();

            if (mSelectedItems.get(position)) v.setBackgroundColor(getContext().getResources().getColor(R.color.blue_50));
            ImageView trashBtn = this.getView().findViewById(R.id.reply_Trash);
            trashBtn.setOnClickListener(v1 -> {
                mSelectedItems.clear();
                deleteMethod();
                toolbarToggle();
                listRefresh();
                setEnabledBtn();
            });
            ImageView replyToClose = this.getView().findViewById(R.id.reply_Close);
            replyToClose.setOnClickListener(v1 -> {
                mSelectedItems.clear();
                selectedItemCount.clear();
                toolbarToggle();
                listRefresh();
                setEnabledBtn();
            });
        }
    }

    private void setEnabledBtn() {
        if (selectedItemCount.size() != 0) {
            ReplyAdapter.setEnabled = false;

        } else {
            ReplyAdapter.setEnabled = true;
        }
    }

    private void countItemsTitle(SparseBooleanArray mSelectedItems) {
        String replyToolbarTitle = selectedItemCount.size() + "개 " + getString(R.string.title_reply_selected);
        toolBarTitle.setText(replyToolbarTitle);
    }

    private void toolbarToggle() {
        if (selectedItemCount.size() == 0) {
            replyToolbar.setVisibility(View.GONE);
            replyEditTextLayout.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).Toolbar(MainActivity.Type.reply);
        } else {
            replyEditTextLayout.setVisibility(View.GONE);
            ((MainActivity) getActivity()).Toolbar(MainActivity.Type.hide);
            replyToolbar.setVisibility(View.VISIBLE);
        }
    }

    private void deleteMethod() {
        for (int i = replyLists.size(); i >= 0; i--) {
            for (int j = 0; j < selectedItemCount.size(); j++) {
                if (i == selectedItemCount.get(j)) {
                    deleteItems(i);
                    selectedItemCount.remove(j);
                }
            }
        }
    }

    private void deleteItems(int num) {
        replyLists.remove(num);
        replyAdapter.notifyItemRemoved(num);
        replyAdapter.notifyItemRangeChanged(num, replyLists.size());
        replyAdapter.notifyDataSetChanged();
    }

    private void appendText(int position) {
        showKeyBoard();
        if (position == -1 || replyId == null) {
            keyboardSet(-1);
            appendBtn(-1);
        } else {
            replyToMethod(position);
        }
        replyAdapter = new ReplyAdapter(getContext(), replyLists, this);
        recyclerView.setAdapter(replyAdapter);
        replyAdapter.notifyDataSetChanged();
    }

    private void replyToMethod(int position) {
        if (replyId != null) {
            showKeyBoard();
            userUUID = UUID.randomUUID().toString();
            replyMessage = replyEditText.getText().toString();
            keyboardSet(position);
            appendBtn(position);
        }
    }

    private void keyboardSet(int position) {
        replyEditText.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (v.getText().length() == 0) {
                    return false;
                } else {
                    replyMessage = replyEditText.getText().toString();
                    userUUID = UUID.randomUUID().toString();
                    if (replyLists.size() == 0 || position == -1 || replyId == null) {
                        replyLists.add(new ReplyList(userUUID, replyMessage));
                    } else {
                        replyLists.add(position + 1, new ReplyList(userUUID, replyMessage));
                        replyLists.get(position + 1).setItemViewType(1);
                        replyAdapter.notifyDataSetChanged();
                        replyId = null;
                    }
                    handled = true;
                    hideKeyBoard();
                    replyEditText.setText("");
                }
            }
            return handled;
        });
    }

    private void appendBtn(int position) {
        replyBtn.setOnClickListener(v -> {
            replyMessage = replyEditText.getText().toString();
            userUUID = UUID.randomUUID().toString();
            if (replyLists.size() == 0 || position == -1 || replyId == null) {
                replyLists.add(new ReplyList(userUUID, replyMessage));
            } else {
                replyLists.add(position + 1, new ReplyList(userUUID, replyMessage));
                replyLists.get(position + 1).setItemViewType(1);
                replyAdapter.notifyDataSetChanged();
                replyId = null;
            }
            hideKeyBoard();
            replyEditText.setText("");
        });
    }

    private void showKeyBoard() {
        if (replyId != null) {
            String text = "@" + replyId.substring(0, 3) + " ";
            replyEditText.setText(text);
            replyEditText.setSelection(replyEditText.length());
        }
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(replyEditText, 0);
    }

    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(replyEditText.getWindowToken(), 0);
    }

    private String period() {
        SimpleDateFormat nowDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        assert getArguments() != null;
        String data = getArguments().getString("date");
        String time = "";
        try {
            assert data != null;
            Date dataDate = nowDate.parse(data);
            Date today = Calendar.getInstance().getTime();
            assert dataDate != null;
            long dateDay = (today.getTime() - dataDate.getTime()) / (24 * 60 * 60 * 1000);
            long dateWeek = dateDay / 7;
            if (dateDay == 0) time = " 오늘";
            else if (dateDay >= 1 && dateDay < 7) time = " " + dateDay + "일";
            else if (dateDay >= 7) time = " " + dateWeek + "주";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private void listRefresh() {
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(replyAdapter);
    }
}
