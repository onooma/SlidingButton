package taxi.tap30.slidingbutton;

/**
 * Created by onooma on 3/12/16.
 */
public class StateValue {
    private String text;
    private int startColor;
    private int endColor;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public StateValue(String text, int startColor, int endColor) {
        this.text = text;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public StateValue() {
    }
}
