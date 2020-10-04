package com.gooliluck.fastfillcustomer.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.gooliluck.fastfillcustomer.R
import com.gooliluck.fastfillcustomer.adapter.UserListAdapter
import com.gooliluck.fastfillcustomer.data.model.User
import com.gooliluck.fastfillcustomer.ui.model.JPNavControl
import kotlinx.android.synthetic.main.fragment_users.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class UsersFragment : BaseFragment() {
    private lateinit var userListAdapter : UserListAdapter
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.setFabShowing(true)
        mainViewModel.currentUser.value = null
        userListAdapter = UserListAdapter(requireContext(),View.OnClickListener {
            val index = it.tag as Int
            mainViewModel.userList.value?.let {userList ->
                val user = userList[index]
                mainViewModel.currentUser.value = user
                val jpNavControl = JPNavControl(R.id.action_UsersFragment_to_UserDetailFragment,null)
                mainViewModel.setJPNavControl(jpNavControl)
            }
        },View.OnClickListener {
            val index = it.tag as Int
            mainViewModel.userList.value?.let {userList ->
                val user = userList[index]
                mainViewModel.currentUser.value = user
                val jpNavControl = JPNavControl(R.id.action_UsersFragment_to_addNewUserFragment,null)
                mainViewModel.setJPNavControl(jpNavControl)
            }
        })
        rvUserList.layoutManager = LinearLayoutManager(requireContext())
        rvUserList.adapter = userListAdapter
        mainViewModel.getUserByPhoneNumber("0983733777")
//        mainViewModel.getUserByName("startbugs")
//        mainViewModel.getUserByName("start")
        mainViewModel.queryUserList.observe(viewLifecycleOwner, Observer { users ->
            Log.e("show user","Query start $users")
            for (user in users) {
                Log.e("show user","user id : ${user.id} and name : ${user.name}")
            }
        })


        mainViewModel.userList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()){
                val newUsers = mutableListOf<User>()
                it.forEach { item -> newUsers.add(item.copy()) }
                userListAdapter.submitList(newUsers)
            }
        })
    }
}
