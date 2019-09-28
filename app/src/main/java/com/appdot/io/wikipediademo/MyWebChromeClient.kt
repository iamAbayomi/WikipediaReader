package com.appdot.io.wikipediademo

import android.webkit.WebChromeClient
import android.webkit.WebView

class MyWebChromeClient: WebChromeClient {

    private var  mListener : ProgressListener? = null

    constructor(listener: ProgressListener) {
       mListener  = listener
    }


    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        mListener?.onUpdateProgress(newProgress)
        super.onProgressChanged(view, newProgress)
    }


   interface  ProgressListener{
       fun onUpdateProgress( progressValue: Int)

}


}
