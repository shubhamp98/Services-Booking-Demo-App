package com.shubhampandey.myapplication.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.data.ServicesDataClass
import com.shubhampandey.myapplication.ui.fragment.HomeFragment

class ServicesAdapter(private val dataSet: List<ServicesDataClass>) :
    RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    private val TAG = ServicesAdapter::class.java.simpleName
    private val homeFragment = HomeFragment()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val serviceCheckBox: CheckBox = view.findViewById(R.id.service_CB)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_service, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    // Replace the contents of a view (invoked by the layout manager)
    // Used to replace/update views at a specific position
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindItems(holder, position)
    }

    private fun bindItems(holder: ViewHolder, position: Int) {
        val service: ServicesDataClass = dataSet[position]
        holder.serviceCheckBox.text = service.serviceName
        holder.serviceCheckBox.setOnClickListener {
            if (holder.serviceCheckBox.isChecked) {
                // Add the service to the list to pass it on next destination
                //Log.i(TAG, "Selected position $position, Data ${holder.serviceCheckBox.text}")
                homeFragment.addService(service)
            }
            else {
                //Log.i(TAG, "Removed position $position, Data ${dataSet[position].serviceName}")
                homeFragment.removeService(service)
            }
        }
    }
}