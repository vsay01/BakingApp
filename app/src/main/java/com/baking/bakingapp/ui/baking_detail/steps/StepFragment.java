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
    private boolean twoPane;

    @BindView(R.id.list_step)
    RecyclerView listStep;

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
        View view = inflater.inflate(R.layout.fragment_layout_step, container, false);

        ButterKnife.bind(this, view);

        if (view.findViewById(R.id.item_step_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true;
        }

        // Set the adapter
        Context context = view.getContext();
        listStep.setLayoutManager(new LinearLayoutManager(context));
        listStep.setAdapter(new MyStepRecyclerViewAdapter(stepWSList, position -> listFragmentInteractionListener.onStepClicked(twoPane, position)));
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
        void onStepClicked(boolean twoPane, int position);
    }
}
