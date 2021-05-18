package com.shubhampandey.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.data.ServicesDataClass
import com.shubhampandey.myapplication.ui.MediatorInterface
import com.shubhampandey.myapplication.ui.adapter.ServicesAdapter
import kotlinx.android.synthetic.main.fragment_home.*

private val servicesOpted = mutableListOf<String>()

class HomeFragment : Fragment(), MediatorInterface {

    lateinit var db: FirebaseFirestore
    private val TAG = HomeFragment::class.java.simpleName
    private val serviceDataset = arrayListOf<ServicesDataClass>()
    private lateinit var customServiceAdapter: ServicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Clear any previous data
        servicesOpted.clear()
        // Access Firestore instance
        db = Firebase.firestore
        //addData()
        setupUI()
    }

    private fun setupUI() {
        setupRecyclerView()
        getDummyData()
//        readData()
        setupListener()
    }

    private fun setupListener() {
        fillForm_Btn.setOnClickListener {
            //Log.i(TAG, "Total services ${servicesOpted.size}")
            if (servicesOpted.isNotEmpty())
                navigateToFormDestination()
            else
                showAtLeastOneServiceRequiredError()
        }
    }

    private fun showAtLeastOneServiceRequiredError() {
        Snackbar.make(requireView(), getString(R.string.atleast_one_service_required_error), Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToFormDestination() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToFormFragment(servicesOpted.toTypedArray())
        findNavController().navigate(action)
    }

    /**
     * Setup the recycler view and attach adapter to it
     */
    private fun setupRecyclerView() {
        // Set layout for RecyclerView
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        serviceList_RV.layoutManager = linearLayoutManager
        customServiceAdapter = ServicesAdapter(serviceDataset)
        // attach adapter
        serviceList_RV.adapter = customServiceAdapter
    }

    private fun getDummyData() {
        for (i in 'A'..'G') {
            serviceDataset.add(
                ServicesDataClass(i.toString())
            )
            //Log.i(TAG, "Service $i")
        }
        serviceList_RV.adapter?.notifyDataSetChanged()
    }

    private fun readData() {
        db.collection(getString(R.string.services_collection_key))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    for(key in document.data.keys) {
                        serviceDataset.add(
                            ServicesDataClass(key)
                        )
                    }
                }
                serviceList_RV.adapter?.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun addData() {
        val services =
            hashMapOf(
                "A" to null,
                "B" to null,
                "C" to null,
                "D" to null,
                "E" to null,
                "F" to null,
                "G" to null
            )
        // Add a new document with a generated ID
        db.collection("services")
            .add(services)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    // Call back from recycler view item click
    override fun addService(service: ServicesDataClass) {
        servicesOpted.add(service.serviceName)
        Log.i(TAG, "Total services ${servicesOpted.size}")
    }

    // Call back from recycler view item click
    override fun removeService(service: ServicesDataClass) {
        servicesOpted.remove(service.serviceName)
        Log.i(TAG, "Total services ${servicesOpted.size}")

    }

}