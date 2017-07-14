/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabsAdapter;
import com.parse.starter.fragments.HomeFragment;
import com.parse.starter.util.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;
    private Toolbar toolbar;
  private ViewPager viewPager;
  private SlidingTabLayout slidingTabLayout;

  @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    toolbar = (Toolbar)findViewById(R.id.toolbar_principal);
    toolbar.setLogo(R.drawable.instagramlogo);
    setSupportActionBar(toolbar);

    viewPager = (ViewPager)findViewById(R.id.vp);
    slidingTabLayout=(SlidingTabLayout)findViewById(R.id.tab);

      TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(),this);
      viewPager.setAdapter(tabsAdapter);

      slidingTabLayout.setCustomTabView(R.layout.tab_layout,R.id.text_item_tab);
      slidingTabLayout.setDistributeEvenly(true);
      slidingTabLayout.setViewPager(viewPager);


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {

    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_main,menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){

      case R.id.action_sair:
        deslogarUsuario();

      case R.id.action_configuracoes:
        return true;

      case R.id.action_compartilhar:
          compartilharFotos();
        return true;

      default:return super.onOptionsItemSelected(item);
    }

  }

    private void compartilharFotos() {

        requestPermissions();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.
                EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,1);
    }

    private void requestPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

            } else {


                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

      if (requestCode==1 && resultCode==RESULT_OK && data!=null){

          //recuperar o local do recurso
          Uri localImagemSelecionada = data.getData();

          //recuperar a imagem do local que foi selecionada

          try {
              Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(),
                      localImagemSelecionada);

              ByteArrayOutputStream stream = new ByteArrayOutputStream();

              imagem.compress(Bitmap.CompressFormat.PNG,75,stream);

              //Criar arquivo no formato parse

              SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss", Locale.US);
              String nomeimagem = dateFormat.format(new Date());

              byte[] byteArray = stream.toByteArray();

              ParseFile arquivoParse = new ParseFile(nomeimagem +"imagem.png", byteArray);

              //Monta o objeto parse

              ParseObject parseObject = new ParseObject("Imagem");
              parseObject.put("username",ParseUser.getCurrentUser().getUsername());
              parseObject.put("imagem", arquivoParse);

              //Salvar dados

              parseObject.saveInBackground(new SaveCallback() {
                  @Override
                  public void done(ParseException e) {
                      if (e ==null){

                          Toast.makeText(getApplicationContext(),"Imagem postada com sucesso",Toast.LENGTH_LONG).show();

                          //Atualizar fragmento
                            TabsAdapter tabsAdapterNovo = (TabsAdapter) viewPager.getAdapter();
                          HomeFragment homeFragment = (HomeFragment)tabsAdapterNovo.getFragment(0);
                          homeFragment.atualizapostagens();


                      }else{
                          Toast.makeText(getApplicationContext(),"Erro ao postar a imagem, tente novamente",Toast.LENGTH_LONG).show();
                      }

                  }
              });
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  }

  private void deslogarUsuario() {
    ParseUser.logOut();
    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
    startActivity(intent);
    finish();
  }
}
