package com.example.menu;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.models.Allergen;
import com.example.menu.models.Item;
import com.example.menu.models.ItemVariant;
import com.example.menu.models.ModifierGroup;
import com.example.menu.models.ModifierOption;
import com.example.menu.repositories.MenuRepository;

import java.util.List;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvVariants = findViewById(R.id.tvVariants);
        TextView tvModifiers = findViewById(R.id.tvModifiers);
        TextView tvAllergens = findViewById(R.id.tvAllergens);
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);

        long itemId = getIntent().getLongExtra("itemId", -1);
        Log.d("DETAILS_DEBUG", "itemId=" + itemId);

        if (itemId == -1) {
            tvTitle.setText("Error: Missing itemId");
            return;
        }

        try {
            MenuRepository repo = new MenuRepository(this);

            Item item = repo.getItemById(itemId);
            List<ItemVariant> variants = repo.getVariants(itemId);
            List<ModifierGroup> groups = repo.getModifierGroups(itemId);
            List<Allergen> allergens = repo.getAllergens(itemId);

            // -------- Title --------
            if (item != null) {
                tvTitle.setText(item.getName());
            } else {
                tvTitle.setText("Item not found");
            }

            // -------- Variants --------
            StringBuilder vb = new StringBuilder();
            vb.append("Variants:\n");
            double basePrice = 0;

            if (variants != null && !variants.isEmpty()) {
                for (ItemVariant v : variants) {
                    vb.append("- ").append(v.getLabel())
                            .append(" : ").append(v.getPrice()).append("\n");
                }
                // ניקח מחיר בסיס = הווריאנט הראשון (אפשר לשפר אח"כ לבחירה)
                basePrice = variants.get(0).getPrice();
            } else {
                vb.append("No variants\n");
            }
            tvVariants.setText(vb.toString());

            // -------- Modifier Groups + Options --------
            StringBuilder mb = new StringBuilder();
            mb.append("Modifier Groups:\n");

            if (groups != null && !groups.isEmpty()) {
                for (ModifierGroup g : groups) {
                    mb.append("\n").append("• ").append(g.getName())
                            .append(" (min=").append(g.getMinSelect())
                            .append(", max=").append(g.getMaxSelect())
                            .append(")\n");

                    List<ModifierOption> options = repo.getModifierOptions(g.getId());
                    if (options != null && !options.isEmpty()) {
                        for (ModifierOption o : options) {
                            mb.append("   - ").append(o.getName())
                                    .append(" (delta ").append(o.getPriceDelta()).append(")\n");
                        }
                    } else {
                        mb.append("   (no options)\n");
                    }
                }
            } else {
                mb.append("No modifier groups\n");
            }
            tvModifiers.setText(mb.toString());

            // -------- Allergens --------
            StringBuilder ab = new StringBuilder();
            ab.append("Allergens:\n");
            if (allergens != null && !allergens.isEmpty()) {
                for (Allergen a : allergens) {
                    ab.append("- ").append(a.getName()).append("\n");
                }
            } else {
                ab.append("No allergens\n");
            }
            tvAllergens.setText(ab.toString());

            // -------- Total Price (כרגע רק מחיר בסיס) --------
            tvTotalPrice.setText("Total Price: " + basePrice);

        } catch (Exception e) {
            tvTitle.setText("Error loading item details");
            tvVariants.setText(e.getMessage());
            tvModifiers.setText("");
            tvAllergens.setText("");
            tvTotalPrice.setText("");
            Log.e("DETAILS_DEBUG", "Error", e);
        }
    }
}