package com.weatherapp.ui.db.fb

import androidx.privacysandbox.tools.core.model.Type
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import com.weatherapp.ui.model.City
import com.weatherapp.ui.model.User

class FBDatabase {

    class FBDatabase {
        interface Listener {
            fun onUserLoaded(user: User)
            fun onCityAdded(city: City)
            fun onCityUpdated(city: City)
            fun onCityRemoved(city: City)
        }
        private val auth = Firebase.auth
        private val db = Firebase.firestore
        private var citiesListReg: ListenerRegistration? = null
        private var listener : Listener? = null

        init {
            auth.addAuthStateListener { auth ->
                if (auth.currentUser == null) {
                    citiesListReg?.remove()
                    return@addAuthStateListener
                }
                val refCurrUser = db.collection("users")
                    .document(auth.currentUser!!.uid)
                refCurrUser.get().addOnSuccessListener {
                    it.toObject(FBUser::class.java)?.let { user ->
                        listener?.onUserLoaded(user.toUser())
                    }
                }
                citiesListReg = refCurrUser.collection("cities")
                    .addSnapshotListener { snapshots, ex ->
                        if (ex != null) return@addSnapshotListener
                        snapshots?.documentChanges?.forEach { change ->
                            val fbCity = change.document.toObject(FBCity::class.java)
                            if (change.type == DocumentChange.Type.ADDED) {
                                listener?.onCityAdded(fbCity.toCity())
                            } else if (change.type == DocumentChange.Type.REMOVED) {
                                listener?.onCityRemoved(fbCity.toCity())
                            }
                        }
                    }
            }
        }

        fun setListener(listener: Listener? = null) {
            this.listener = listener
        }

        fun register(user: User) {
            if (auth.currentUser == null)
                throw RuntimeException("User not logged in!")
            val uid = auth.currentUser!!.uid
            db.collection("users").document(uid + "").set(user.toFBUser());
        }

        fun add(city: City) {
            if (auth.currentUser == null)
                throw RuntimeException("User not logged in!")
            val uid = auth.currentUser!!.uid
            db.collection("users").document(uid).collection("cities")
                .document(city.name).set(city.toFBCity())
        }

        fun remove(city: City) {
            if (auth.currentUser == null)
                throw RuntimeException("User not logged in!")
            val uid = auth.currentUser!!.uid
            db.collection("users").document(uid).collection("cities")
                .document(city.name).delete()
        }
    }
}
