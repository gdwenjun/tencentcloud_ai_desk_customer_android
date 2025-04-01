package com.tencent.qcloud.tuikit.deskcustomerserviceplugin.classicui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.qcloud.tuikit.deskcommon.component.impl.GlideEngine;
import com.tencent.qcloud.tuikit.deskchat.bean.ChatInfo;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.R;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.TUICustomerServicePluginService;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.component.CommonPhrasesPopupCard;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceConfig;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUICustomerServiceProductInfo;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.config.TUIInputViewFloatLayerData;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.presenter.TUICustomerServicePresenter;
import com.tencent.qcloud.tuikit.deskcustomerserviceplugin.util.TUICustomerServiceLog;

import java.util.Arrays;
import java.util.List;

public class InputViewFloatLayerProxy {
    private RecyclerView rvFloatLayer;
    private ChatInfo chatInfo;
    public InputViewFloatLayerProxy(ChatInfo chatInfo) {
        this.chatInfo = chatInfo;
//         initDataList();
    }

    private void initDataList() {
        List<TUIInputViewFloatLayerData> dataList = TUICustomerServiceConfig.getInstance().getInputViewFloatLayerDataList();
        TUIInputViewFloatLayerData productInfoData = new TUIInputViewFloatLayerData();
        productInfoData.setContent(TUICustomerServicePluginService.getAppContext().getString(R.string.send_product_info));
        productInfoData.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
                TUICustomerServiceProductInfo productInfo = TUICustomerServiceConfig.getInstance().getProductInfo();
                presenter.sendProductMessage(chatInfo.getId(), productInfo);
            }
        });
        dataList.add(productInfoData);

        TUIInputViewFloatLayerData commonPhrasesData = new TUIInputViewFloatLayerData();
        commonPhrasesData.setContent(TUICustomerServicePluginService.getAppContext().getString(R.string.common_phrases));
        commonPhrasesData.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showCommonPhrasesView();
            }
        });
        dataList.add(commonPhrasesData);

        TUICustomerServiceConfig.getInstance().setInputViewFloatLayerDataList(dataList);
    }

    public void showFloatLayerContent(ViewGroup viewGroup) {
        if (chatInfo == null) {
            return;
        }

        viewGroup.setVisibility(View.VISIBLE);
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.input_view_float_layer_content_view, viewGroup);
        rvFloatLayer = viewGroup.findViewById(R.id.rv_float_layer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(viewGroup.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvFloatLayer.setLayoutManager(linearLayoutManager);

        List<TUIInputViewFloatLayerData> dataList = TUICustomerServiceConfig.getInstance().getInputViewFloatLayerDataList();
        FloatLayerAdapter floatLayerAdapter = new FloatLayerAdapter(dataList);
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

    public void showFloatLayerContentForProduct(ViewGroup viewGroup){
        if (chatInfo == null) {
            return;
        }
        viewGroup.setVisibility(View.VISIBLE);
//        viewGroup.setPadding(30,20,30,20);
        viewGroup.setBackgroundColor(Color.TRANSPARENT);
        LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_card, viewGroup);
        ImageView  ivPic = viewGroup.findViewById(R.id.iv_pic);

        TUICustomerServiceProductInfo productInfo = TUICustomerServiceConfig.getInstance().getProductInfo();
        TextView tvHeader = viewGroup.findViewById(R.id.product_title);
        TextView tvDesc = viewGroup.findViewById(R.id.product_desc);
        tvHeader.setText(productInfo.getName());
        tvDesc.setText(productInfo.getDescription());
        GlideEngine.loadImageSetDefault(ivPic, productInfo.getPictureUrl(), R.drawable.product_picture_fail);
        TUICustomerServiceLog.i("renderProduct",productInfo.getName());
    }

    private void showCommonPhrasesView() {
        CommonPhrasesPopupCard commonPhrasesPopupCard = new CommonPhrasesPopupCard((Activity) rvFloatLayer.getContext());
        String[] commonPhrasesArray = TUICustomerServicePluginService.getAppContext().getResources().getStringArray(R.array.common_phrases_list);
        List<String> dataList = Arrays.asList(commonPhrasesArray);
        commonPhrasesPopupCard.setDataList(dataList);
        commonPhrasesPopupCard.setOnClickListener(new CommonPhrasesPopupCard.OnClickListener() {
            @Override
            public void onClick(String content) {
                TUICustomerServicePresenter presenter = new TUICustomerServicePresenter();
                presenter.sendTextMessage(chatInfo.getId(), content);
            }
        });

        commonPhrasesPopupCard.show(rvFloatLayer, Gravity.BOTTOM);
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
            view.setBackground(getGradientDrawable(view));
            ViewHolder holder = new ViewHolder(view);
            return holder;
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
