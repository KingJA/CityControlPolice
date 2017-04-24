package com.tdr.citycontrolpolice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.citycontrolpolice.R;
import com.tdr.citycontrolpolice.dao.DbDaoXutils3;
import com.tdr.citycontrolpolice.entity.Basic_Dictionary_Kj;
import com.tdr.citycontrolpolice.entity.ChuZuWu_AdminList;
import com.tdr.citycontrolpolice.entity.ChuZuWu_ComprehensiveInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String,String> typeMap=new HashMap<>();

    public AdminsAdapter(Context context, List<ChuZuWu_AdminList.ContentBean.AdminListBean> list) {
        super(context, list);
        List<Basic_Dictionary_Kj> adminTypeList = (List<Basic_Dictionary_Kj>) DbDaoXutils3.getInstance().selectAllWhere(Basic_Dictionary_Kj.class, "COLUMNCODE", "ADMINTYPE");
        Log.e("AdminsAdapter", "adminTypeList: "+adminTypeList.size());
        for (Basic_Dictionary_Kj bean : adminTypeList) {
            typeMap.put(bean.getCOLUMNVALUE(),bean.getCOLUMNCOMMENT());
            Log.e("AdminsAdapter", "bean.getCOLUMNCOMMENT(): "+bean.getCOLUMNCOMMENT() );
        }
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
        viewHolder.tv_type.setText(typeMap.get(list.get(position).getADMINTYPE()+""));

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
        public final TextView tvadminname;
        public final TextView tvadmincard;
        public final TextView tv_type;
        public final View root;

        public ViewHolder(View root) {
            ivdelete = (ImageView) root.findViewById(R.id.iv_delete);
            tvadminname = (TextView) root.findViewById(R.id.tv_admin_name);
            tvadmincard = (TextView) root.findViewById(R.id.tv_admin_card);
            tv_type = (TextView) root.findViewById(R.id.tv_type);
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
