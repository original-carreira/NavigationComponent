package com.example.navigationcomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigationcomponent.iu.arquivos.ArquivoFragment
import com.example.navigationcomponent.iu.start.StartFragment

class MainActivity : AppCompatActivity(), StartFragment.OnButtonclick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.Container_fragmento,StartFragment.newInstance())
            .addToBackStack("StartFragment")
            .commit()
    }

    override fun buttonClicked() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.Container_fragmento,ArquivoFragment.newInstace())
            .addToBackStack("ArquivoFragment")
            .commit()
    }
}