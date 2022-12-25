package com.example.stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.stats.databinding.ActivityMainBinding
import com.example.stats.fragments.PortfolioFragment
import com.example.stats.fragments.WatchlistFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(binding.fragment.id, PortfolioFragment.newInstance()).commit()

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.portfolio -> {
                    supportFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(binding.fragment.id, PortfolioFragment.newInstance()).commit()
                }
                R.id.wachlist -> {
                    supportFragmentManager.beginTransaction().addToBackStack(null)
                        .replace(binding.fragment.id, WatchlistFragment.newInstance()).commit()
                }
                R.id.market -> {
                    Toast.makeText(applicationContext, "Market", LENGTH_SHORT).show()
                    // TODO: доделать переключение
                }
                R.id.account -> {
                    Toast.makeText(applicationContext, "Account", LENGTH_SHORT).show()
                    // TODO: доделать переключение
                }
            }
            true
        }

//        val assetDao_ = ADatabase.getDatabase(application).assetDao()

        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.portfolio -> {
                }
                R.id.wachlist -> {
                }

                R.id.market -> {
                }
                R.id.account -> {
                }
            }
            true
        }
    }
}