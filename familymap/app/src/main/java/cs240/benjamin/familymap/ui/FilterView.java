package cs240.benjamin.familymap.ui;

import android.widget.CompoundButton;

/**
 * Created by benjamin on 4/5/16.
 */
public class FilterView {
    private CompoundButton.OnCheckedChangeListener listener;
    private String subText;
    private String superText;
    private boolean enabled;

    public FilterView()
    {
        this.subText = "";
        this.superText = "";
        this.listener = null;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public CompoundButton.OnCheckedChangeListener getListener() {
        return listener;
    }

    public void setListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public String getSuperText() {
        return superText;
    }

    public void setSuperText(String superText) {
        this.superText = superText;
    }
}


