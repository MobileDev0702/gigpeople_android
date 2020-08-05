package com.gigpeople.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigpeople.app.R;
import com.gigpeople.app.adapter.ChatAdapter;
import com.gigpeople.app.adapter.FavouriteAdapter;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.ChatListResponse;
import com.gigpeople.app.model.ChatModel;
import com.gigpeople.app.utils.GlobalMethods;
import com.gigpeople.app.utils.PrefConnect;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.recycler_chat)
    RecyclerView recyclerChat;
    Unbinder unbinder;
    ChatAdapter chatAdapter;
    List<ChatListResponse.ChatList> chat_list;
    ApiService apiService;
    String user_id = "1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = RetrofitSingleton.createService(ApiService.class);
        user_id= PrefConnect.readString(getContext(),PrefConnect.USER_ID,"");

        return view;
    }

    private void setChatList() {
        chat_list = new ArrayList<>();
        Log.e("ChatListReq", "\nUserId: " + user_id);
        Call<ChatListResponse> call = apiService.callchatListAPI(user_id);
        call.enqueue(new Callback<ChatListResponse>() {
            @Override
            public void onResponse(Call<ChatListResponse> call, Response<ChatListResponse> response) {
                Log.e("ChatListResp", new Gson().toJson(response.body()));
                if (response.isSuccessful()) {
                    ChatListResponse resp = response.body();
                    if (resp != null) {
                        if (resp.getStatus().equals("1")) {
                            chat_list = resp.getChatList();

                            if (recyclerChat != null) {
                                chatAdapter = new ChatAdapter(getContext(), chat_list);
                                recyclerChat.setAdapter(chatAdapter);
                                recyclerChat.setLayoutManager(new LinearLayoutManager(getContext()));
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ChatListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (GlobalMethods.isNetworkAvailable(getContext())) {
            setChatList();
        } else {
            GlobalMethods.Toast(getContext(), getString(R.string.internet));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
