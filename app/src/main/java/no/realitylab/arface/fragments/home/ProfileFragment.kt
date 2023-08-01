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
import org.w3c.dom.Text


class ProfileFragment : Fragment() {

    private lateinit var userImage: ImageView
    private lateinit var userNameCard: TextView
    private lateinit var userPhone: TextView
    private lateinit var userEmailCard: TextView

    private val userViewModel : UserViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userObserver = Observer<UserData> { userData ->
            Picasso.get().load(userData.profilePictureUrl).into(userImage)
            userNameCard.text = userData.userName
            userEmailCard.text = userData.userEmail
            userPhone.text = "---"
        }

        userViewModel.userData.observe(this, userObserver)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflate = inflater.inflate(R.layout.fragment_profile, container, false)
        userImage = inflate.findViewById(R.id.iv_profile_photo)
        userNameCard = inflate.findViewById(R.id.tv_profile_username_card)
        userPhone = inflate.findViewById(R.id.tv_profile_phone)
        userEmailCard = inflate.findViewById(R.id.tv_profile_email_card)
        return inflate
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle()
            }
    }
}