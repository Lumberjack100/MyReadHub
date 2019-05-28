package com.dragon.myreadhub.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.dragon.myreadhub.R;
import com.dragon.myreadhub.activity.AboutActivity;
import com.dragon.myreadhub.activity.LayoutTestActivity;
import okhttp3.*;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment
{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    @BindView(R.id.textResponse)
    TextView textResponse;

    private Handler mHandler = new Handler();


    public NewsFragment()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2)
    {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);


        return rootView;
    }


    @OnClick({R.id.btnTest,R.id.textResponse})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnTest:

//                try
//                {
//                    testOkhttp("https://blog.csdn.net/lmj623565791/article/details/47911083");
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//

                LayoutTestActivity.startActivity(getActivity());
                break;

            case R.id.textResponse:

//                try
//                {
//                    testOkhttp("https://blog.csdn.net/lmj623565791/article/details/47911083");
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//

                AboutActivity.startActivity(getActivity());
                break;

            default:
                break;
        }
    }


    private void testOkhttp(String url) throws IOException
    {
        OkHttpClient mOkHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                String ss = "";
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException
            {
                String responseStr = response.body().string();

                mHandler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        textResponse.setText(responseStr);
                    }
                });
            }
        });
    }


}
