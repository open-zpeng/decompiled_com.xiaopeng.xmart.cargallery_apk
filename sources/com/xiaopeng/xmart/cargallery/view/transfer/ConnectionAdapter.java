package com.xiaopeng.xmart.cargallery.view.transfer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.xmart.cargallery.CameraLog;
import com.xiaopeng.xmart.cargallery.R;
import com.xiaopeng.xmart.cargallery.bean.ConnectionBean;
import com.xiaopeng.xmart.cargallery.helper.ClickTooQuickHelper;
import com.xiaopeng.xui.widget.XListTwo;
import java.util.List;
/* loaded from: classes21.dex */
public class ConnectionAdapter extends RecyclerView.Adapter<ConnectionVH> {
    private static final String CLICK_CONNECTION_ITEM = "click_connection_item";
    private static final String TAG = "ConnectionAdapter";
    Context mContext;
    List<ConnectionBean> mData;
    OnItemClickListener mListener;

    /* loaded from: classes21.dex */
    public interface OnItemClickListener {
        void onItemClick(ConnectionBean connection);
    }

    public ConnectionAdapter(List<ConnectionBean> data) {
        this.mData = data;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ConnectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.item_wifi_connection, parent, false);
        return new ConnectionVH(view);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ConnectionVH holder, final int position) {
        holder.connect.setText(this.mData.get(position).getDeviceName());
        holder.connect.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.xmart.cargallery.view.transfer.-$$Lambda$ConnectionAdapter$jNldVXZ9p8bG2dwh9_lhcLtTNig
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ConnectionAdapter.this.lambda$onBindViewHolder$0$ConnectionAdapter(position, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$ConnectionAdapter(final int position, View v) {
        if (ClickTooQuickHelper.getInstance().isClickTooQuick(CLICK_CONNECTION_ITEM)) {
            CameraLog.d(TAG, "Click too quick,click item", false);
            return;
        }
        OnItemClickListener onItemClickListener = this.mListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(this.mData.get(position));
            this.mData.get(position).setConnecting(true);
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mData.size();
    }

    public void setmListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes21.dex */
    public static class ConnectionVH extends RecyclerView.ViewHolder {
        XListTwo connect;

        ConnectionVH(View itemView) {
            super(itemView);
            this.connect = (XListTwo) itemView.findViewById(R.id.xlt_connect);
        }
    }
}
