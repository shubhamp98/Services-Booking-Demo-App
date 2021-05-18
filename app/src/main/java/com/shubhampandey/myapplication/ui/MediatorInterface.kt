package com.shubhampandey.myapplication.ui

import com.shubhampandey.myapplication.data.ServicesDataClass

/**
 * Using interface to communicate from recycler view to fragment
 */
interface MediatorInterface {
    fun addService(service: ServicesDataClass)

    fun removeService(service: ServicesDataClass)
}