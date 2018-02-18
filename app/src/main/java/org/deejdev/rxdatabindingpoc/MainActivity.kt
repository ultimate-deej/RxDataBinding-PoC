package org.deejdev.rxdatabindingpoc

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.deejdev.rxdatabindingpoc.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.model = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
}

class MainViewModel : ViewModel() {
    private var number = 0L

    val everIncreasingNumberString: Observable<String> = Observable.interval(0, 300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
            .map { number++.toString() }
}
