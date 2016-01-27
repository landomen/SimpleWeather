package landomen.com.simpleweather.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import landomen.com.simpleweather.R;

/**
 * Created by Domen on 27. 01. 2016.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {

    private ArrayList<String> cities;

    public CityAdapter(ArrayList<String> cities) {
        this.cities = cities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txTitle.setText(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            txTitle = (TextView) itemView.findViewById(R.id.list_item_city_title);
        }
    }
}
