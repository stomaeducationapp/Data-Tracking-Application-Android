package Medical_Data_Input;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import capstonegroup2.dataapp.R;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 08/02/2019
 * LAST MODIFIED BY - Jeremy Dunnet 08/02/2019
 */

/* CLASS/FILE DESCRIPTION
 * This is the recyclerview for adding bags to the screen during multiple inputs with fragments
 */

/* VERSION HISTORY
 * 08/02/2019 - Created class
 */

/* REFERENCES
 * Idea sourced from https://www.androidhive.info/2016/05/android-working-with-card-view-and-recycler-view/
 * Developer tutorials from https://developer.android.com/
 */

public class BagAdapter extends RecyclerView.Adapter<BagAdapter.MyViewHolder> implements Serializable /*So we can attach to bundles*/ {


    private List<Bag> bagList;
    private ItemDeleteInterface itemDeleteInterface;

    public BagAdapter(List<Bag> finesList, ItemDeleteInterface mainActivity)
    {

        this.bagList = finesList;
        this.itemDeleteInterface = mainActivity; //Attach interface implementation
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView consis, amount, time;
        public ImageButton deleteButt;

        public MyViewHolder(View view) {
            super(view);
            consis = (TextView) view.findViewById(R.id.bagListConsis); //Find all the layout objects in the fine row
            amount = (TextView) view.findViewById(R.id.bagListAmount);
            time = (TextView) view.findViewById(R.id.bagListTime);
            deleteButt = (ImageButton) view.findViewById(R.id.delete_entry_button);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bag_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int pos = position;
        Bag bag = bagList.get(pos); //Get the fine object and assign all the values to the layout objects

        holder.consis.setText(bag.getConsistency());
        String amount = "" + bag.getAmount();
        holder.amount.setText(amount);
        holder.time.setText(bag.getTime());

        holder.deleteButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemDeleteInterface.deleteRecyclerItem(pos); //Set up listener for deleting the item
            }
        });

    }

    @Override
    public int getItemCount() {
        return bagList.size();
    }

    /* FUNCTION INFORMATION
     * NAME - removeAt
     * INPUTS - position (position of the element to be deleted)
     * OUTPUTS - none
     * PURPOSE - This is the function that facilitates removal of a member from the list
     */
    public void removeAt(int position) {
        bagList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bagList.size()); //Need to tell adapter to reorganise list order (in case element had elements after it)
    }

    /* AUTHOR INFORMATION
     * CREATOR - Jeremy Dunnet 08/02/2019
     * LAST MODIFIED BY - Jeremy Dunnet 08/02/2019
     */

    /* CLASS/FILE DESCRIPTION
     * This is the recyclerview interface for the ability to remove an item from the adapter and list
     */

    /* VERSION HISTORY
     * 08/02/2019 - Created file
     */

    /* REFERENCES
     * RecyclerView Item Removal/Addition Interface learned from https://stackoverflow.com/questions/26076965/android-recyclerview-addition-removal-of-items
     * Developer tutorials from https://developer.android.com/
     */
    public interface ItemDeleteInterface
    {
        void deleteRecyclerItem(int pos);
    }

}
