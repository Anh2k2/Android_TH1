    package com.example.myapplicationtest;

    import android.app.Activity;
    import android.content.Context;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.BaseAdapter;
    import android.widget.CheckBox;
    import android.widget.CompoundButton;
    import android.widget.Filter;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;


    import androidx.appcompat.app.AppCompatActivity;

    import java.util.ArrayList;

    public class Adapter extends BaseAdapter

    {

        private ArrayList<Contact> data;
        private Activity context;
        private LayoutInflater inflater;
        private ArrayList<Contact> databackup;

        public Adapter(){}
        public Adapter(ArrayList<Contact> data, Activity context) {
            this.data = data;
            this.context = context;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return data.get(i).getId();
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v = view;
            if(v == null) {
                v = inflater.inflate(R.layout.layoutdata, null);

                ImageView img = v.findViewById(R.id.imageView);
                ImageView imgphone = v.findViewById(R.id.imageView2);
                ImageView imgsms = v.findViewById(R.id.imageView3);
                CheckBox check = v.findViewById(R.id.checkBox);
                check.setChecked(data.get(i).isCheck());

                imgphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentCall = new Intent();
                        intentCall.setAction(Intent.ACTION_DIAL);
                        intentCall.setData(Uri.parse("tel:" + data.get(i).getPhone()));
                        context.startActivity(intentCall);
                    }
                });
                imgsms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentSms = new Intent();
                        intentSms.setAction(Intent.ACTION_SENDTO);
                        intentSms.setData(Uri.parse("smsto:" + data.get(i).getPhone()));
                        context.startActivity(intentSms);
                    }
                });
                check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        //Code khi trạng thái check thay đổi
                        if(b){
                            data.get(i).setCheck(true);
                        }else {
                            data.get(i).setCheck(false);
                        }
                    }
                });
                TextView name = v.findViewById(R.id.textView);
                TextView phone = v.findViewById(R.id.textView2);
                name.setText(data.get(i).getName());
                phone.setText(data.get(i).getPhone());
            }
            return v;
        }
        public Filter getFilter() {
            Filter f = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults fr = new FilterResults();
                    //Backup dữ liệu: lưu tạm data vào databackup
                    if(databackup==null)
                        databackup = new ArrayList<>(data);
                    //Nếu chuỗi để filter là rỗng thì khôi phục dữ liệu
                    if(charSequence==null || charSequence.length()==0)
                    {
                        fr.count = databackup.size();
                        fr.values = databackup;
                    }
                    //Còn nếu không rỗng thì thực hiện filter
                    else{
                        ArrayList<Contact> newdata = new ArrayList<>();
                        for(Contact u:databackup)
                            if(u.getName().toLowerCase().contains(
                                    charSequence.toString().toLowerCase()))
                                newdata.add(u);
                        fr.count=newdata.size();
                        fr.values=newdata;
                    }
                    return fr;
                }
                @Override
                protected void publishResults(CharSequence charSequence,
                                              FilterResults filterResults) {
                    data = new ArrayList<Contact>();
                    ArrayList<Contact> tmp =(ArrayList<Contact>)filterResults.values;
                    for(Contact u: tmp)
                        data.add(u);
                    notifyDataSetChanged();
                }
            };
            return f;
        }
    }
