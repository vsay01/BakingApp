package com.baking.bakingapp.ui.baking_detail.steps;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.bakingapp.R;
import com.baking.bakingapp.data.models.StepWS;
import com.baking.bakingapp.ui.baking_detail.step_detail.StepDetailFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StepFragment extends Fragment {

    // TODO: Customize parameter argument names
    public static final String ARG_ITEM_ID = "item_id";
    private List<StepWS> stepWSList;
    private OnListFragmentInteractionListener listFragmentInteractionListener;

    @BindView(R.id.list)
    RecyclerView listIngredient;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StepFragment newInstance(List<StepWS> stepWSList) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ITEM_ID, (ArrayList<StepWS>) stepWSList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            stepWSList = getArguments().getParcelableArrayList(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_list, container, false);

        ButterKnife.bind(this, view);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            listIngredient.setLayoutManager(new LinearLayoutManager(context));
            listIngredient.setAdapter(new MyStepRecyclerViewAdapter(stepWSList, position -> listFragmentInteractionListener.onStepClicked(position)));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepFragment.OnListFragmentInteractionListener) {
            listFragmentInteractionListener = (StepFragment.OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listFragmentInteractionListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onStepClicked(int position);
    }
}
