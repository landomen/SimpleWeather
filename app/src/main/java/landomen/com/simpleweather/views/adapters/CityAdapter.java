package landomen.com.simpleweather.views.adapters;

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
    private CityClickListener cityClickListener;

    public CityAdapter(ArrayList<String> cities) {
        this.cities = new ArrayList<>(cities);
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

    /**
     * Adds a new item to the underlying dataset.
     *
     * @param item Item to add
     */
    public void addItem(int position, String item) {
        cities.add(position, item);
        notifyDataSetChanged();
    }

    /**
     * Removes the item from the dataset at specified position.
     *
     * @param position
     */
    public void removeItem(int position) {
        cities.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * Changes the whole dataset.
     *
     * @param items
     */
    public void setItems(ArrayList<String> items) {
        cities = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    /**
     * Gets the item from the underlying dataset.
     *
     * @param position Index of the item
     * @return
     */
    public String getItem(int position) {
        return cities.get(position);
    }

    /**
     * Sets the click listener for items.
     *
     * @param cityClickListener Callback for click events
     */
    public void setOnClickListener(CityClickListener cityClickListener) {
        this.cityClickListener = cityClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            txTitle = (TextView) itemView.findViewById(R.id.list_item_city_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cityClickListener.onCityClicked(getAdapterPosition());
        }
    }

    /**
     * Interface for callback when click events happen.
     */
    public interface CityClickListener {
        void onCityClicked(int position);
    }
}
