package me.pedroguimaraes.xkcd.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import me.pedroguimaraes.xkcd.R
import me.pedroguimaraes.xkcd.api.XkcdApiInterface
import me.pedroguimaraes.xkcd.util.loadUrl

class MainActivity : AppCompatActivity() {
    private var disposable: Disposable? = null

    private val xkcdApiServe by lazy {
        XkcdApiInterface.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchLatestComic()
    }

    private fun fetchLatestComic() {
        disposable = xkcdApiServe.getLatestComic()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            comicView.loadUrl(result.img)
                            comicAltText.text = result.alt
                        },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}