package com.example.agricultureexpertsapp.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.agricultureexpertsapp.GlobalHelper;
import com.example.agricultureexpertsapp.R;
import com.example.agricultureexpertsapp.models.FmessageModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    LayoutInflater layoutInflater;
    View view;
    List<FmessageModel> fmessageModelList;
    //    OnLoadMoreListener mOnLoadMoreListener;
    private final int VIEW_TYPE_MY_MESSAGE = 0;
    private final int VIEW_TYPE_MY_IMAGE = 1;
    private final int VIEW_TYPE_FRIEND_MESSAGE = 2;
    private final int VIEW_TYPE_FRIEND_IMAGE = 3;
    private final int VIEW_TYPE_LOADING = 4;

    private boolean isLoading;
    private int visibleThreshold = 30;
    private int lastVisibleItem, totalItemCount;
    boolean show_loading = true;
    RecyclerView rv;
    private Activity activity;
    private String userId;

    final String TAG = "conv_adapter";

    CollectionReference docRef;

    final String TEXT_MESSAGE = "text";
    final String IMAGE_MESSAGE = "image";

    //    private MemberModel user;
//    private String avatar;
//    private int chatId;
    private DocumentSnapshot lastVisible;

    public MessageAdapter(Activity activity, RecyclerView rv, String userId, List<FmessageModel> fmessageModelList, CollectionReference collectionReference) {

        this.activity = activity;
        this.fmessageModelList = fmessageModelList;
        this.docRef = collectionReference;
        this.rv = rv;
//        this.avatar = avatar;
        this.userId = userId;

//        user = UtilityApp.getUserData();

//        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv.getLayoutManager();
//
//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//
//                showLoadMore();
//                if (show_loading) {
//                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold) && totalItemCount >= visibleThreshold) {
//                        if (mOnLoadMoreListener != null) {
//                            mOnLoadMoreListener.onLoadMore();
//                        }
//                        isLoading = true;
//                    }
//                }
//
//            }
//
//        });
//        setOnloadListener();

        initRealTimeListener();


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_MY_MESSAGE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_message_my, viewGroup, false);
            return new MyMessageViewHolder(view);
        } else if (viewType == VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_message_friend, viewGroup, false);
            return new FriendMessageViewHolder(view);
        }
//        else if (viewType == VIEW_TYPE_LOADING) {
//            View view = LayoutInflater.from(activity).inflate(R.layout.row_loading, viewGroup, false);
//
//            return new LoadingViewHolder(view);
//        }

        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MyMessageViewHolder) {
            MyMessageViewHolder myMessageViewHolder = (MyMessageViewHolder) holder;

            if (fmessageModelList != null) {

                FmessageModel fmessageModel = fmessageModelList.get(position);

//            myMessageViewHolder.myMessageTxt.setText(GlobalData.LinkfiyTextView(activity, fmessageModel.msg));
                myMessageViewHolder.myMessageTxt.setText(fmessageModel.content);
                Date date;
                if (fmessageModel.date != null)
                    date = fmessageModel.date;
                else
                    date = fmessageModel.my_date;
                myMessageViewHolder.timeTxt.setText(GlobalHelper.GetTimeString(date));

            }

        } else if (holder instanceof FriendMessageViewHolder) {
            FriendMessageViewHolder friendMessageViewHolder = (FriendMessageViewHolder) holder;

            if (fmessageModelList != null) {

                FmessageModel fmessageModel = fmessageModelList.get(position);

//            friendMessageViewHolder.friendMessageTxt.setText(GlobalData.LinkfiyTextView(activity, fmessageModel.msg));

                friendMessageViewHolder.friendMessageTxt.setText(fmessageModel.content);
                Date date;
                if (fmessageModel.date != null)
                    date = fmessageModel.date;
                else
                    date = fmessageModel.my_date;
                friendMessageViewHolder.timeTxt.setText(GlobalHelper.GetTimeString(date));

//                Glide.with(activity)
//                        .asBitmap()
//                        .load(avatar)
//                        .apply(new RequestOptions()
//                                .placeholder(R.drawable.error_logo)
//                        )
//                        .into(friendMessageViewHolder.userImg);
            }

        }


    }


    @Override
    public int getItemCount() {
        if (fmessageModelList != null)
            return fmessageModelList.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {

//        if (position == 0 || position == 2) {
//            return VIEW_TYPE_FRIEND_MESSAGE;
//        } else {
//            return VIEW_TYPE_MY_MESSAGE;
//        }
        if (fmessageModelList.get(position) == null) {
            return VIEW_TYPE_LOADING;

        } else if (fmessageModelList.get(position).user_id.equals(userId)) {
//            if (fmessageModelList.get(position).type.equals(IMAGE_MESSAGE)) {
//                return VIEW_TYPE_MY_IMAGE;
//            } else {
            return VIEW_TYPE_MY_MESSAGE;
//            }

        } else {
//            if (fmessageModelList.get(position).type.equals(IMAGE_MESSAGE)) {
//                return VIEW_TYPE_FRIEND_IMAGE;
//            } else {
            return VIEW_TYPE_FRIEND_MESSAGE;
//            }
        }

    }

    class FriendMessageViewHolder extends RecyclerView.ViewHolder {

        LinearLayout friendMessageLY;
        TextView friendMessageTxt;
        TextView timeTxt;
//        private ImageView userImg;

        public FriendMessageViewHolder(View itemView) {
            super(itemView);

            friendMessageLY = itemView.findViewById(R.id.friendMessageLY);
            friendMessageTxt = itemView.findViewById(R.id.friendMessageTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
//            userImg = itemView.findViewById(R.id.userImg);


//            boolean isLeftToRight = activity.getResources().getBoolean(R.bool.is_left_to_right);
//            if (isLeftToRight) {
//                arrowIcon.setScaleX(1);
//                arrowIcon.setRotation(-30);
//            } else {
//                arrowIcon.setScaleX(-1);
//                arrowIcon.setRotation(30);
//            }

        }
    }

    class MyMessageViewHolder extends RecyclerView.ViewHolder {


        LinearLayout myMessageLY;
        TextView myMessageTxt;
        TextView timeTxt;
//        private ImageView arrowIcon;

        public MyMessageViewHolder(View itemView) {
            super(itemView);

            myMessageLY = itemView.findViewById(R.id.myMessageLY);
            myMessageTxt = itemView.findViewById(R.id.myMessageTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
//            arrowIcon = itemView.findViewById(R.id.arrowIcon);


//            boolean isLeftToRight = activity.getResources().getBoolean(R.bool.is_left_to_right);
//            if (isLeftToRight) {
//                arrowIcon.setScaleX(-1);
//                arrowIcon.setRotation(30);
//            } else {
//                arrowIcon.setScaleX(1);
//                arrowIcon.setRotation(-30);
//            }

        }
    }

//    class LoadingViewHolder extends RecyclerView.ViewHolder {
//        public ProgressBar progressBar;
//
//        public LoadingViewHolder(View itemView) {
//            super(itemView);
//            progressBar = itemView.findViewById(R.id.progressBar1);
//        }
//
//    }

    private void initRealTimeListener() {

        LoadData(true);


//        docRef.orderBy("date", Query.Direction.DESCENDING).limit(5).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value,
//                                @Nullable FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w(TAG, "Listen failed.", e);
//                    return;
//                }
//
//                for (DocumentChange doc : value.getDocumentChanges()) {
//
//                    switch (doc.getType()) {
//                        case ADDED:
//
//                            try {
//
//                                lastVisible = value.getDocuments()
//                                        .get(value.size() - 1);
//
//                                FmessageModel fmessageModel = doc.getDocument().toObject(FmessageModel.class);
//
//                                if (fmessageModelList != null) {
//                                    int pos = 0;
//                                    pos = fmessageModelList.size();
//                                    fmessageModelList.add(fmessageModel);
//
//                                    if (getAdapter() != null) {
//                                        getAdapter().notifyItemInserted(pos);
////                                        rv.scrollToPosition(fmessageModelList.size() - 1);
//                                    }
//                                }
//
////                                if (lastVisible != null)
////                                    LoadData();
//
//                                Log.i(TAG, doc.getDocument().getId() + " => " + doc.getDocument().getData() + " lastVisible " + lastVisible.getId());
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                            break;
//                        case MODIFIED:
//
////                                    Log.d(TAG, doc.getDocument().getId() + " => " + doc.getDocument().getData());
//
//                            break;
//                        case REMOVED:
////                                    Log.d(TAG, "Removed city: " + doc.getDocument().getData());
//                            break;
//                    }
//
//                }
//
//            }
//        });

    }

    public void setLoaded() {
        isLoading = false;
    }


    public void LoadData(final boolean isFirst) {
        Query query;
        query = docRef
                .orderBy("date", Query.Direction.DESCENDING);
//        if (isFirst) {
//            query = docRef
//                    .orderBy("date", Query.Direction.DESCENDING)
//                    .limit(visibleThreshold);
//        } else {
//            query = docRef
//                    .orderBy("date", Query.Direction.DESCENDING)
//                    .startAfter(lastVisible)
//                    .limit(visibleThreshold);
//        }

        query.addSnapshotListener((value, e) -> {

            if (isLoading) {
                fmessageModelList.remove(0/*fmessageModelList.size() - 1*/);
                notifyItemRemoved(0/*fmessageModelList.size()*/);
            }


            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            if ((value != null ? value.size() : 0) == 0)
                show_loading = false;
            List<FmessageModel> messagesList = new ArrayList<>();
//            int pos ;
            boolean isAdd = false;
            for (DocumentChange doc : value.getDocumentChanges()) {

                switch (doc.getType()) {
                    case ADDED:

                        try {
                            Log.i(TAG, "Log isLoading " + isLoading);

                            lastVisible = value.getDocuments()
                                    .get(value.size() - 1);

                            FmessageModel fmessageModel = doc.getDocument().toObject(FmessageModel.class);

//                            if (fmessageModel.date == null) {
//                                fmessageModel.date = DateHandler.GetDateNow();
//                            }

                            messagesList.add(fmessageModel);

                            isAdd = true;

                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case MODIFIED:
//                                    Log.d(TAG, doc.getDocument().getId() + " => " + doc.getDocument().getData());
                        break;
                    case REMOVED:
//                                    Log.d(TAG, "Removed city: " + doc.getDocument().getData());
                        break;
                }

            }

//            if (isAdd && messagesList.size() == 1) {
//                UtilityApp.setLastMessage(chatId, messagesList.get(0).msg);
//            }

            if (fmessageModelList != null) {
//                Collections.reverse(messagesList);

                if (isLoading) {
                    fmessageModelList.addAll(0, messagesList);
                    notifyItemRangeInserted(0, messagesList.size());
                } else {
                    fmessageModelList.addAll(0, messagesList);
                    notifyItemRangeInserted(0, messagesList.size());
                    rv.scrollToPosition(0);
                }

            }

            if (isLoading)
                setLoaded();

        });


    }

    public MessageAdapter getAdapter() {
        return this;
    }

}

