package com.se.sws;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.se.sws.Adapter.PostAdapter;
import com.se.sws.Model.Post;

import java.util.ArrayList;

public class InstitutionsActivity extends AppCompatActivity {
    boolean flag = false; // Is user admin or not
    String isAdmin;
    Intent _intent; // Usage in multiple functions
    ImageView mCreatePostsFab; // Background
    String current_uid;
    String post_author;
    String UserName;
    String inst = null;
    String from;
    String user_query;
    ImageView popupButton;
    private FirebaseAuth firebaseAuth;
    ArrayList<String> admins = new ArrayList<>();


    RecyclerView mRecyclerView; // For scrolling
    StaggeredGridLayoutManager staggeredGridLayoutManager; // The way posts are shown

    /**
     * FB information - user & fireStore
     */
    //FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Will be used for representatives of each university
    FirebaseFirestore firebaseFirestore;

    /**
     * We connect FB and the app together here
     */
    FirestoreRecyclerAdapter<Post, InstitutionsActivity.PostViewHolder> postAdapter;
    FirestoreRecyclerOptions<Post> allUsersPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        admins.add("0ugfd4IASOhTglFc2GZZCNqWuIs2");
        admins.add("fkPHB9gJl5dusFpK1mWK6ua4nO52");
        admins.add("qvys0yDcNIX266hi86DNkKOkKNM2");

        _intent = getIntent();
        flag = _intent.getBooleanExtra("isAdmin", false); // Is the user admin or not
        post_author = _intent.getStringExtra("model_uid");
        UserName = _intent.getStringExtra("UserName");
        inst = _intent.getStringExtra("ins");
        user_query = _intent.getStringExtra("desc"); // if the intent from search_post;
        from = _intent.getStringExtra("from"); // where to go back in back arrow

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser(); // Will be used for representatives of each university
        firebaseFirestore = FirebaseFirestore.getInstance();
        assert firebaseUser != null;
        current_uid = firebaseUser.getUid();


        int layout = -1;
        int v = -1;

        if (inst == null) {
            inst = "BackUp";
        }

        switch (inst) {
            case "AR":
                layout = R.layout.activity_ariel_university;
                v = R.id.imageAddPostMain_AR;
                break;
            case "BGU":
                layout = R.layout.activity_ben_gurion;
                v = R.id.imageAddPostMain_BG;
                break;
            case "Haifa":
                layout = R.layout.activity_haifa_uni;
                v = R.id.imageAddPostMain_Haifa;
                break;
            case "Reicman":
                layout = R.layout.activity_reichman_uni;
                v = R.id.imageAddPostMain_reicman;
                break;
            case "Technion":
                layout = R.layout.activity_technion_uni;
                v = R.id.imageAddPostMain_Technion;

                break;
            case "tlv":
                layout = R.layout.activity_tel_aviv_uni;
                v = R.id.imageAddPostMain_tlv;
                break;
            case "heb":
                layout = R.layout.activity_hebrew_uni;
                v = R.id.imageAddPostMain_Heb;
                break;
            default:
                layout = R.layout.backup;
                v = R.id.imageAddPostMain_reicman;
        }

        setContentView(layout);

        View _view = findViewById(v); // We click on Ariel University board add post button ('(+)' button)
        mCreatePostsFab = (ImageView) _view;
        for (int i = 0; i < admins.size(); i++) {
            if (current_uid.equals(admins.get(i))) {
                flag = true;
            }
        }


        ImageView imageAddItemMain = (ImageView) _view; //
        imageAddItemMain.setOnClickListener(view -> { // '+'
            Intent intent = new Intent(getApplicationContext(), PostActivity.class);
            intent.putExtra("University", inst); // Pass variables to AddProducts activity
            intent.putExtra("isAdmin", flag);
            intent.putExtra("uid", current_uid);
            intent.putExtra("UserName", UserName);
            startActivity(intent);
        });


        if (inst == null) {
            inst = "BackUp";
            System.out.println("inst:" + inst);

        }
        System.out.println("inst:" + inst);
        System.out.println("user_query:");
        System.out.println(user_query);


        Query query;
        if (user_query != null) { //check if there is a query from search_post screen
            query = firebaseFirestore.collection("Universities").document(inst)
                    .collection("All").orderBy("title", Query.Direction.ASCENDING).startAt(user_query).endAt(user_query + "\uf8ff");
        }
        else {
            query = firebaseFirestore.collection("Universities").document(inst)
                    .collection("All")
                    .orderBy("title", Query.Direction.ASCENDING);
        }

        // Connect the query above with the adapter declared on the start
        allUsersPosts = new FirestoreRecyclerOptions.Builder<Post>().setQuery(query, Post.class).build();

        // Fill the information needed of the adapter
        postAdapter = new FirestoreRecyclerAdapter<Post, InstitutionsActivity.PostViewHolder>(allUsersPosts) {


            @Override
            protected void onBindViewHolder(@NonNull InstitutionsActivity.PostViewHolder holder, int position, @NonNull Post model) {

                popupButton = holder.itemView.findViewById(R.id.menupopbutton); // The 3 dots on the right of each post
                // The information shown on each small post
                holder.title.setText(model.getTitle());
                holder.content.setText(model.getContent());
                holder.phone.setText(model.getPhone());
                holder.uid.setText(model.getUid());

                // So we know which post to delete
                String docId = postAdapter.getSnapshots().getSnapshot(position).getId();

                /*
                 * When post is being pressed and want to be extended
                 */
                holder.itemView.setOnClickListener(v -> {
                    // Post details activity - pass all the information to PostAdapter class
                    Intent intent = new Intent(v.getContext(), PostAdapter.class);
                    intent.putExtra("university", inst);
                    intent.putExtra("title", model.getTitle());
                    intent.putExtra("content", model.getContent());
                    intent.putExtra("phone", model.getPhone());
                    intent.putExtra("model_uid", model.getUid()); // Current post id
                    intent.putExtra("isAdmin", flag);
                    intent.putExtra("noteId", docId);
                    intent.putExtra("uid", current_uid);

                    v.getContext().startActivity(intent);
                });

                // If user is not an admin will not be able to see the 3 dots & delete a post (part of Admin Panel)
                if (!current_uid.equals(model.getUid())) {
                    popupButton.setVisibility(View.GONE);
                }
                if (flag) {
                    popupButton.setVisibility(View.VISIBLE);
                }

                /*
                 * 3 dots button
                 */
                popupButton.setOnClickListener(v -> {

                    PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                    popupMenu.setGravity(Gravity.END);
                    // Delete button is shown whenever being pressed
                    popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(InstitutionsActivity.this);
                            builder.setTitle("Delete");
                            builder.setMessage("Are you sure you want to delete?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                // We choose to delete
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DocumentReference documentReference = firebaseFirestore.collection("Universities").document(inst).collection("All").document(docId);
                                    documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(v.getContext(), "Post deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(v.getContext(), "Failed To Delete", Toast.LENGTH_SHORT).show();
                                        }

                                    });
                                    DocumentReference refBackUp = firebaseFirestore.collection("Universities").document("BackUp").collection("All").document(docId);
                                    refBackUp.delete();

                                }
                            });
                            // Do nothing when "no" is pressed
                            builder.setNegativeButton("No", (dialogInterface, i) -> {

                            });
                            builder.create().show();
                            return false;
                        }
                    });
                    popupMenu.show();
                });
            }

            @NonNull
            @Override
            public InstitutionsActivity.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_layout, parent, false);
                return new InstitutionsActivity.PostViewHolder(view);
            }
        };
        // After post is deleted do these below
        mRecyclerView = findViewById(R.id.postsRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setAdapter(postAdapter);

    } //here..


    /**
     * Inner class of posts, used in the function above
     */
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView content;
        private final TextView phone;
        private final TextView uid;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            content = itemView.findViewById(R.id.textSubtitle);
            phone = itemView.findViewById(R.id.textPhoneNumber);
            uid = itemView.findViewById(R.id.textAuthorName);


        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        postAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (postAdapter != null) {
            postAdapter.stopListening();
        }
    }

    // Returns to universities menu
    public void backUniversities(View view) {
        Intent intent;
        intent = new Intent(InstitutionsActivity.this, UniversitiesActivity.class); // back to UniversitiesActivity
        intent.putExtra("isAdmin", flag); // If the user who clicked on the posts is an admin or not
        startActivity(intent);
    }

    public void backSearchPage(View view) {
        Intent intent;
        intent = new Intent(InstitutionsActivity.this, searchPostActivity.class); // back to search_post
        intent.putExtra("isAdmin", flag); // If the user who clicked on the posts is an admin or not
        startActivity(intent);

    }

}
