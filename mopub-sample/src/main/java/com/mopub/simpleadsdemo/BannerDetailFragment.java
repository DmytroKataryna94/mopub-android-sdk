package com.mopub.simpleadsdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;

import static com.mopub.mobileads.MoPubView.BannerAdListener;
import static com.mopub.simpleadsdemo.Utils.hideSoftKeyboard;
import static com.mopub.simpleadsdemo.Utils.logToast;

public class BannerDetailFragment extends Fragment implements BannerAdListener {
    // custom banner view
    private MoPubView mMoPubView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // моделька MoPubSampleAdUnit з 5 полями(AD_UNIT_ID ,DESCRIPTION ,AD_TYPE ,IS_USER_DEFINED ,ID)
        final MoPubSampleAdUnit adConfiguration =
                MoPubSampleAdUnit.fromBundle(getArguments());

        final View view = inflater.inflate(R.layout.banner_detail_fragment, container, false);

        // клас з 4 вюшками , які формують вигляд banner_detail_fragment layout
        final DetailFragmentViewHolder views = DetailFragmentViewHolder.fromView(view);
        mMoPubView = (MoPubView) view.findViewById(R.id.banner_mopubview);
        hideSoftKeyboard(views.mKeywordsField);

        final String adUnitId = adConfiguration.getAdUnitId();
        views.mDescriptionView.setText(adConfiguration.getDescription());
        views.mAdUnitIdView.setText(adUnitId);
        views.mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String keywords = views.mKeywordsField.getText().toString();
                loadMoPubView(adUnitId, keywords);
            }
        });
        mMoPubView.setBannerAdListener(this);
        loadMoPubView(adUnitId, null);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mMoPubView != null) {
            mMoPubView.destroy();
            mMoPubView = null;
        }
    }

    // закидуємо unit ID і keywords(хз що то , якийсь текс що описується в EditText banner_detail_fragment layout)
    // mMoPubView set data to class : AdViewController ||| class params ID , KEYWORDS , LOCATION , WIDTH
    // loadAd trigger AdViewController method internalLoadAd()
    private void loadMoPubView(final String adUnitId, final String keywords) {
        mMoPubView.setAdUnitId(adUnitId);
        mMoPubView.setKeywords(keywords);
        mMoPubView.loadAd();
    }

    // BannerAdListener
    @Override
    public void onBannerLoaded(MoPubView banner) {
        logToast(getActivity(), "Banner loaded.");
    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {
        final String errorMessage = (errorCode != null) ? errorCode.toString() : "";
        logToast(getActivity(), "Banner failed to load: " + errorMessage);
    }

    @Override
    public void onBannerClicked(MoPubView banner) {
        logToast(getActivity(), "Banner clicked.");
    }

    @Override
    public void onBannerExpanded(MoPubView banner) {
        logToast(getActivity(), "Banner expanded.");
    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {
        logToast(getActivity(), "Banner collapsed.");
    }
}
