package devsec.app.rhinhorealestates.utils.services

import devsec.app.rhinhorealestates.data.models.Estate

class Cart {
    companion object {
        var cart: ArrayList<String> = ArrayList()

        var cartRemovedItems: ArrayList<String> = ArrayList()
    }
}