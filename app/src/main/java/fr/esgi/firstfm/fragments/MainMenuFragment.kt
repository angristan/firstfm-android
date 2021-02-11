package fr.esgi.firstfm.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import fr.esgi.firstfm.*
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        when (activity?.javaClass?.simpleName) {
            ProfileActivity::class.simpleName -> {
                bottomNavigationView.menu.getItem(0).isChecked = true
                bottomNavigationView.menu.getItem(0)
                    .setIcon(R.drawable.ic_menu_user_solid_foreground)
            }

            GlobalActivity::class.simpleName -> {
                bottomNavigationView.menu.getItem(1).isChecked = true
                bottomNavigationView.menu.getItem(1)
                    .setIcon(R.drawable.ic_menu_main_solid_foreground)
            }
            MusicScannerActivity::class.simpleName -> {
                bottomNavigationView.menu.getItem(2).isChecked = true
                bottomNavigationView.menu.getItem(2)
                    .setIcon(R.drawable.ic_menu_mic_solid_foreground)
            }
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.profile -> activity?.let { it1 ->
                    if (!it.isChecked) {
                        RouterActivity.navigateTo(it1)
                    }
                }
                R.id.home -> activity?.let { it1 ->
                    if (!it.isChecked) {
                        GlobalActivity.navigateTo(it1)
                    }
                }
                R.id.scanner -> activity?.let { it1 ->
                    if (!it.isChecked) {
                        MusicScannerActivity.navigateTo(it1)
                    }
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu to use in the action bar
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

}
