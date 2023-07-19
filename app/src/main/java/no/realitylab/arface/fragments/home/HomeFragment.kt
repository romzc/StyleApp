package no.realitylab.arface.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.image_view
import no.realitylab.arface.R
import no.realitylab.arface.models.UserData
import no.realitylab.arface.viewmodels.UserViewModel


class HomeFragment : Fragment() {

    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var profileImage: ImageView
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userObserver = Observer<UserData> {userData ->
            Picasso.get().load(userData.profilePictureUrl).into(profileImage)
            userName.text = userData.userName
        }
        userViewModel.userData.observe(this, userObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_home, container, false)
        profileImage = inflate.findViewById(R.id.image_view)
        userName = inflate.findViewById(R.id.tv_username)
        return inflate
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle()
            }
    }
}