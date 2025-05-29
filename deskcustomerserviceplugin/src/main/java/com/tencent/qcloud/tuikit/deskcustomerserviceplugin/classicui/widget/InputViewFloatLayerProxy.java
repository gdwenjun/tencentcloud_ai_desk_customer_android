package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskcommon.util.ThreadUtils;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServicePluginService;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.component.CommonPhrasesPopupCard;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceConfig;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceProductInfo;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUIInputViewFloatLayerData;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceLog;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class InputViewFloatLayerProxy {
    private RecyclerView rvFloatLayer;
    private ChatInfo chatInfo;
    private ViewGroup rootView;
    private LinearLayout linearLayout;
    private FloatLayerAdapter floatLayerAdapter;
    public InputViewFloatLayerProxy() {

    }

    public void init(ChatInfo chatInfo, ViewGroup viewGroup) {
        this.chatInfo = chatInfo;
        this.rootView = viewGroup;

        // 创建 LinearLayout
        linearLayout = new LinearLayout(rootView.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        linearLayout.setGravity(android.view.Gravity.CENTER);
        rootView.addView(linearLayout);
    }

    // 快捷回复
    public void showFloatLayerContent() {
        if (chatInfo == null) {
            return;
        }

        this.rootView.setVisibility(View.VISIBLE);
        View quickView = LayoutInflater.from(this.rootView.getContext()).inflate(R.layout.input_view_float_layer_content_view, null);
        LinearLayout.LayoutParams quickViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.addView(quickView, quickViewParams);
        rvFloatLayer = quickView.findViewById(R.id.rv_float_layer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(quickView.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvFloatLayer.setLayoutManager(linearLayoutManager);

        List<TUIInputViewFloatLayerData> dataList = TUICustomerServiceConfig.getInstance().getInputViewFloatLayerDataList();
        floatLayerAdapter = new FloatLayerAdapter(dataList);
        rvFloatLayer.setAdapter(floatLayerAdapter);
        floatLayerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TUIInputViewFloatLayerData data = dataList.get(position);
                if(data.getOnItemClickListener()!=null){
                    data.getOnItemClickListener().onItemClick(view, position);
                }
            }
        });
    }

    public void updateHumanServiceVis(boolean isVis)  {
        if (floatLayerAdapter == null) {
            return;
        }
        floatLayerAdapter.updateItem(0,isVis);
    }
    // 商品卡片
    public void showFloatLayerContentForProduct(){
        if (chatInfo == null) {
            return;
        }
        this.rootView.setVisibility(View.VISIBLE);
        this.rootView.setBackgroundColor(Color.TRANSPARENT);
        View productView = LayoutInflater.from(this.rootView.getContext()).inflate(R.layout.product_card, null);
        LinearLayout.LayoutParams productViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        if (!TUICustomerServiceConfig.getInstance().getInputViewFloatLayerDataList().isEmpty()) {
            productViewParams.bottomMargin = 10;
        }
        linearLayout.addView(productView, productViewParams);

        ImageView  ivPic = productView.findViewById(R.id.iv_pic);
        TUICustomerServiceProductInfo productInfo = TUICustomerServiceConfig.getInstance().getProductInfo();
        TextView tvHeader = productView.findViewById(R.id.product_title);
        TextView tvDesc = productView.findViewById(R.id.product_desc);
        tvHeader.setText(productInfo.getName());
        tvDesc.setText(productInfo.getDescription());
        GlideEngine.loadImageSetDefault(ivPic, productInfo.getPictureUrl(), R.drawable.product_picture_fail);
        TUICustomerServiceLog.i("renderProduct",productInfo.getName());
        productView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productInfo.getClickAutoSend()) {
                    TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
                    presenter.sendProductMessage(chatInfo.getId(), productInfo);
                }
                if (productInfo.getOnItemClickListener()!=null){
                    productInfo.getOnItemClickListener().onItemClick(view.findViewById(R.id.product_wraper),0,null);
                }

            }
        });
    }

    private GradientDrawable getGradientDrawable(View itemView) {
        // 创建 GradientDrawable 对象
        GradientDrawable gradientDrawable = new GradientDrawable();

        float radiusInDp = 16;
        float radiusInPixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, radiusInDp, itemView.getResources().getDisplayMetrics());
        gradientDrawable.setCornerRadius(radiusInPixels);
        gradientDrawable.setColor(itemView.getResources().getColor(android.R.color.white));
        return gradientDrawable;
    }

    public class FloatLayerAdapter extends RecyclerView.Adapter<FloatLayerAdapter.ViewHolder> {
        private List<TUIInputViewFloatLayerData> mDataList;
        private OnItemClickListener onItemClickListener;
        private View rootView;
        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvFloatLayer;
            ImageView tvFloatIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                tvFloatLayer = itemView.findViewById(R.id.tv_float_layer);
                tvFloatIcon = itemView.findViewById(R.id.tv_float_icon);
                tvFloatIcon.setVisibility(View.GONE);
                tvFloatIcon.setBackground(getGradientDrawable(itemView));
            }
        }

        public FloatLayerAdapter(List<TUIInputViewFloatLayerData> dataList) {
            this.mDataList = dataList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.float_layer_item, parent, false);
            rootView = view;
            view.setBackground(getGradientDrawable(view));
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        public void updateItem(int position, boolean isVis)  {
            if(mDataList == null || mDataList.size() <= position || rvFloatLayer == null) {
                return;
            }
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rvFloatLayer.stopScroll();
                    rvFloatLayer.setNestedScrollingEnabled(false);
                    TUIInputViewFloatLayerData newItem = mDataList.get(position);
                    newItem.setVisible(isVis);
                    mDataList.set(position, newItem);
                    notifyItemChanged(position);
                    rvFloatLayer.setNestedScrollingEnabled(true);
                }
            });
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            TUIInputViewFloatLayerData data = mDataList.get(position);
            if (data.getIconResourceId() != -1) {
                holder.tvFloatIcon.setVisibility(View.VISIBLE);
                holder.tvFloatIcon.setImageResource(data.getIconResourceId());
            } else {
                holder.tvFloatIcon.setVisibility(View.GONE);
            }
            holder.tvFloatLayer.setText(data.getContent());

            holder.tvFloatLayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(view, holder.getBindingAdapterPosition());
                    }
                }
            });
            if (data.isDefault()) {
                int visible = data.isVisible() && TUICustomerServiceConfig.getInstance().getIsShowHumanService()? View.VISIBLE:View.GONE;
                holder.tvFloatLayer.setText(rootView.getContext().getString(R.string.common_human_service));
                data.setContent(rootView.getContext().getString(R.string.common_human_service));
                // 不想显示人工服务，可直接在这里修改隐藏
                holder.tvFloatIcon.setVisibility(visible);
                holder.tvFloatLayer.setVisibility(visible);
            }
        }

        @Override
        public int getItemCount() {
            if (mDataList != null) {
                return mDataList.size();
            } else {
                return 0;
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
