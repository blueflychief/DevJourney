package com.doandkeep.devjourney.main.weibo.timeline.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.doandkeep.devjourney.R;
import com.doandkeep.devjourney.base.MyApplication;
import com.doandkeep.devjourney.bean.BaseWeiboResponse;
import com.doandkeep.devjourney.bean.request.weibo.RepostBody;
import com.doandkeep.devjourney.bean.weibo.Timeline;
import com.doandkeep.devjourney.bean.weibo.WeiboTimeline;
import com.doandkeep.devjourney.retrofit.ServiceGenerator;
import com.doandkeep.devjourney.retrofit.service.WeiboService;
import com.doandkeep.devjourney.third.weibo.AccessTokenKeeper;
import com.doandkeep.devjourney.util.DebugLog;
import com.doandkeep.devjourney.view.AdvanceImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhangtao on 16/8/5.
 */
public class WeiboTimelineView extends FrameLayout {

    private static final String TAG = WeiboTimelineView.class.getCanonicalName();

    @BindView(R.id.timeline_user_profile_iv)
    AdvanceImageView mUserProfileIV;
    @BindView(R.id.timeline_user_name_tv)
    TextView mUserNameTV;
    @BindView(R.id.timeline_create_source_tv)
    TextView mCreateSourceTV;
    @BindView(R.id.timeline_text_tv)
    TextView mTextTV;
    @BindView(R.id.timeline_picture_iv)
    AdvanceImageView mPictureIV;
    @BindView(R.id.timeline_repost_count_tv)
    TextView mRepostCountTV;
    @BindView(R.id.timeline_comment_count_tv)
    TextView mCommentCountTV;

    private WeiboTimeline mTimeline;

    public WeiboTimelineView(Context context) {
        super(context);
        init(context);
    }

    public WeiboTimelineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.view_weibo_timeline, this);
        ButterKnife.bind(this, this);
    }

    public void setData(WeiboTimeline timeline) {
        this.mTimeline = timeline;

        AdvanceImageView.ImageViewSpec spec = new AdvanceImageView.ImageViewSpec();
        spec.setCircle(true);
        mUserProfileIV.setUrl(timeline.getUser().getProfile_image_url(), spec);
        mUserNameTV.setText(timeline.getUser().getScreen_name());
        mCreateSourceTV.setText(timeline.getCreated_at() + ",来自" + timeline.getSource());
        mTextTV.setText(timeline.getText());
        if (!TextUtils.isEmpty(timeline.getOriginal_pic())) {
            mPictureIV.setVisibility(View.VISIBLE);
            mPictureIV.setUrl(timeline.getOriginal_pic());
        } else {
            mPictureIV.setVisibility(View.GONE);
        }
        mRepostCountTV.setText(timeline.getReposts_count() + "");
        mCommentCountTV.setText(timeline.getComments_count() + "");
    }

    @OnClick(R.id.timeline_repost_layout)
    public void repost() {
        WeiboService weiboService = ServiceGenerator.createService(WeiboService.class);
        Call<WeiboTimeline> call = weiboService.repost(mTimeline.getId());

        call.enqueue(new Callback<WeiboTimeline>() {
            @Override
            public void onResponse(Call<WeiboTimeline> call, Response<WeiboTimeline> response) {
            }

            @Override
            public void onFailure(Call<WeiboTimeline> call, Throwable t) {
            }
        });


    }
}