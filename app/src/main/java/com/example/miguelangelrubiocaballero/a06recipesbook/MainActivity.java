package com.example.miguelangelrubiocaballero.a06recipesbook;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    public static final String API_KEY = "6ae5a7e021c4198d86919533e8174738";
    public static List<IngredientsResources> ingredients;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ingredients = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        requestData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            IngredientsResources ingredientsObj = ingredients.get(getArguments().getInt(ARG_SECTION_NUMBER));
            TextView textView = rootView.findViewById(R.id.title_TextView);
            textView.setText(ingredientsObj.getTitle());


            ListView listView = rootView.findViewById(R.id.listView);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, ingredientsObj.getIngredients());
            listView.setAdapter(arrayAdapter);

            ImageView imageView = rootView.findViewById(R.id.imageView);

            Glide.with(this).load(ingredientsObj.getImageUrl()).into(imageView);

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }
    }

    private void requestData()
    {
        RestService service = RestService.retrofit.create(RestService.class);
        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<Recipe> call = service.getTopRecipes(API_KEY, "r");

        call.enqueue(new Callback<Recipe>() {
            @Override
            public void onResponse(Call<Recipe> call, Response<Recipe> response) {
                Recipe recipe = response.body();
                if(recipe != null) {
                    Log.d("response", recipe.getCount().toString());
                    for(int i = 0; i < 3; i++) {
                        requestRecipeData(recipe.getRecipesResources().get(i).getRecipeId());
                    }
                }
            }
            @Override
            public void onFailure(Call<Recipe> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }
    private void requestRecipeData(String rId)
    {
        RestService service = RestService.retrofit.create(RestService.class);
        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<Ingredients> call = service.getRecipe(API_KEY, rId);

        call.enqueue(new Callback<Ingredients>() {
            @Override
            public void onResponse(Call<Ingredients> call, Response<Ingredients> response) {
                Ingredients ingredientsResponse = response.body();
                if(ingredientsResponse != null) {
                    Log.d("response", ingredientsResponse.getIngredientsResources().getTitle());
                    ingredients.add(ingredientsResponse.getIngredientsResources());
                    mSectionsPagerAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<Ingredients> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }
}
