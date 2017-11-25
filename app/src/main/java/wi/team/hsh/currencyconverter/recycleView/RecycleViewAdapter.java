package wi.team.hsh.currencyconverter.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import wi.team.hsh.currencyconverter.DataManagement;
import wi.team.hsh.currencyconverter.R;

/**
 * Created by amin on 08.11.17.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter <RecycleViewAdapter.MyRecycleViewHolder>{


    private final LayoutInflater inflater;
    List<DataManagement> data = Collections.emptyList(); //no nullpointer Exception

    //Contructor for Inflater
    public RecycleViewAdapter(Context context, List<DataManagement> data ){
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_single_row,parent,false);
        MyRecycleViewHolder recycleViewHolder = new MyRecycleViewHolder(view);
        return recycleViewHolder;
    }

    //DIsplay Data at position
    @Override
    public void onBindViewHolder(MyRecycleViewHolder holder, int position) {
        DataManagement currentObject = data.get(position);
        holder.tvTitle.setText(currentObject.title);
        holder.tvDescription.setText(currentObject.tvDescription);
        holder.image.setImageResource(currentObject.iconId);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    //VIEWHOLDER : so you dont have to create views again and again for single row items
    class MyRecycleViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvDescription;
        ImageView image;

        public MyRecycleViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.listTextName);
            tvDescription = (TextView) itemView.findViewById(R.id.listTextDescription);
             image = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}
