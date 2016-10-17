package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AdminList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;

import java.util.List;

import butterknife.Bind;

/**
 * 项目名称：物联网城市防控(警用版)
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/3/25 17:19
 * 修改备注：
 */
public class AdminsAdapter extends BaseSimpleAdapter<ChuZuWu_AdminList.ContentBean.AdminListBean> {
    @Bind(R.id.iv_delete)
    ImageView ivDelete;
    @Bind(R.id.tv_admin_name)
    TextView tvAdminName;
    @Bind(R.id.tv_admin_card)
    TextView tvAdminCard;
    private OnAdminDeleteListener onAdminDeleteListener;

    public AdminsAdapter(Context context, List<ChuZuWu_AdminList.ContentBean.AdminListBean> list) {
        super(context, list);
    }

    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_czf_admins, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvadminname.setText(list.get(position).getNAME());
        viewHolder.tvadmincard.setText(list.get(position).getIDENTITYCARD());
        viewHolder.ivcheckBy.setBackgroundResource(list.get(position).getSOURCE()==1?R.drawable.bg_police:R.drawable.transparency_full);
        viewHolder.ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onAdminDeleteListener != null) {
                    onAdminDeleteListener.onDelete(list.get(position).getIDENTITYCARD(), position);
                }
            }
        });
        return convertView;
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }


    public class ViewHolder {
        public final ImageView ivdelete;
        public final ImageView ivcheckBy;
        public final TextView tvadminname;
        public final TextView tvadmincard;
        public final View root;

        public ViewHolder(View root) {
            ivdelete = (ImageView) root.findViewById(R.id.iv_delete);
            ivcheckBy = (ImageView) root.findViewById(R.id.iv_checkBy);
            tvadminname = (TextView) root.findViewById(R.id.tv_admin_name);
            tvadmincard = (TextView) root.findViewById(R.id.tv_admin_card);
            this.root = root;
        }
    }

    public interface OnAdminDeleteListener{
        void onDelete(String cardId,int position);
    }

    public void setOnAdminDeleteListener(OnAdminDeleteListener onAdminDeleteListener) {
        this.onAdminDeleteListener = onAdminDeleteListener;
    }
}
