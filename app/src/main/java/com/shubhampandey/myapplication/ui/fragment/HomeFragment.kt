package com.shubhampandey.myapplication.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.shubhampandey.myapplication.R
import com.shubhampandey.myapplication.data.ServicesDataClass
import com.shubhampandey.myapplication.ui.adapter.ServicesAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_service.*

class HomeFragment : Fragment() {

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

        // Access Firestore instance
        db = Firebase.firestore
        //addData()
        setupUI()
    }

    private fun setupUI() {
        setupRecyclerView()
        //getDummyData()
        setupListener()
        readData()

    }

    private fun setupListener() {
        fillForm_Btn.setOnClickListener {
            navigateToFormDestination()
        }
    }

    private fun navigateToFormDestination() {
        val action =
            HomeFragmentDirections.actionHomeFragmentToFormFragment()
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
            Log.i(TAG, "Service $i")
        }
        serviceList_RV.adapter?.notifyDataSetChanged()
    }

    private fun readData() {
        db.collection("services")
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
        // Create a new user with a first and last name
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
}