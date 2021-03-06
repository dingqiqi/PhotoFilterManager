package com.lakala.appcomponent.photofilter.internal.entity;

import android.content.pm.ActivityInfo;
import android.support.annotation.StyleRes;

import com.lakala.appcomponent.photofilter.BuildConfig;
import com.lakala.appcomponent.photofilter.MimeType;
import com.lakala.appcomponent.photofilter.R;
import com.lakala.appcomponent.photofilter.engine.ImageEngine;
import com.lakala.appcomponent.photofilter.engine.impl.GlideEngine;
import com.lakala.appcomponent.photofilter.filter.Filter;
import com.lakala.appcomponent.photofilter.listener.OnCheckedListener;
import com.lakala.appcomponent.photofilter.listener.OnGetPathListListener;
import com.lakala.appcomponent.photofilter.listener.OnSelectedListener;

import java.util.List;
import java.util.Set;

public final class SelectionSpec {

    public Set<MimeType> mimeTypeSet;
    public boolean mediaTypeExclusive;
    public boolean showSingleMediaType;
    @StyleRes
    public int themeId;
    public int orientation;
    public boolean countable;
    public int maxSelectable;
    public int maxImageSelectable;
    public int maxVideoSelectable;
    public List<Filter> filters;
    public boolean capture;
    public CaptureStrategy captureStrategy;
    public int spanCount;
    public int gridExpectedSize;
    public float thumbnailScale;
    public ImageEngine imageEngine;
    public boolean hasInited;
    public boolean setFilter;
    public OnSelectedListener onSelectedListener;
    public OnGetPathListListener onGetPathListListener;
    public boolean originalable;
    public boolean autoHideToobar;
    public int originalMaxSize;
    public OnCheckedListener onCheckedListener;

    private SelectionSpec() {
    }

    public static SelectionSpec getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public static SelectionSpec getCleanInstance() {
        SelectionSpec selectionSpec = getInstance();
        selectionSpec.reset();
        return selectionSpec;
    }

    private void reset() {
        mimeTypeSet = null;
        mediaTypeExclusive = true;
        showSingleMediaType = false;
        themeId = R.style.Matisse_Zhihu;
        orientation = 0;
        countable = false;
        maxSelectable = 1;
        maxImageSelectable = 0;
        maxVideoSelectable = 0;
        filters = null;
        capture = false;
        captureStrategy = null;
        spanCount = 3;
        gridExpectedSize = 0;
        thumbnailScale = 0.5f;
        imageEngine = new GlideEngine();
        hasInited = true;
        originalable = false;
        autoHideToobar = false;
        originalMaxSize = Integer.MAX_VALUE;
        setFilter = true;
        captureStrategy = new CaptureStrategy(true, BuildConfig.APPLICATION_ID + ".file_provider", "Photo");

    }

    public boolean singleSelectionModeEnabled() {
        return !countable && (maxSelectable == 1 || (maxImageSelectable == 1 && maxVideoSelectable == 1));
    }

    public boolean needOrientationRestriction() {
        return orientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED;
    }

    public boolean onlyShowImages() {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypeSet);
    }

    public boolean onlyShowVideos() {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypeSet);
    }

    private static final class InstanceHolder {
        private static final SelectionSpec INSTANCE = new SelectionSpec();
    }
}
