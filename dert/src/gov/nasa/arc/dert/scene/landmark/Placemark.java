package gov.nasa.arc.dert.scene.landmark;

import gov.nasa.arc.dert.icon.Icons;
import gov.nasa.arc.dert.scenegraph.BillboardMarker;
import gov.nasa.arc.dert.state.MapElementState;
import gov.nasa.arc.dert.state.MapElementState.Type;
import gov.nasa.arc.dert.state.PlacemarkState;
import gov.nasa.arc.dert.util.ImageUtil;
import gov.nasa.arc.dert.util.StringUtil;

import java.awt.Color;
import java.util.Properties;

import javax.swing.Icon;

import com.ardor3d.image.Texture;
import com.ardor3d.math.Vector3;

/**
 * Provides a place marker map element
 *
 */
public class Placemark extends BillboardMarker implements Landmark {

	public static final Icon icon = Icons.getImageIcon("placemark.png");

	// Defaults
	public static Color defaultColor = Color.yellow;
	public static double defaultSize = 1.0f;
	public static int defaultTextureIndex = 0;
	public static boolean defaultLabelVisible = true;
	public static boolean defaultPinned = false;

	// Icon selections
	public static final String[] ICON_NAME = { "pushpin", "flag" };
	public static final String[] ICON_LABEL = { "Pushpin", "Flag" };

	// Texture selections
	protected static final Texture[] nominalTexture = new Texture[ICON_NAME.length];
	protected static final Texture[] highlightTexture = new Texture[ICON_NAME.length];

	protected int textureIndex = -1;
	protected PlacemarkState state;

	/**
	 * Constructor
	 * 
	 * @param state
	 */
	public Placemark(PlacemarkState state) {
		super(state.name, state.position, state.size, state.color, state.labelVisible, state.pinned);
		setTexture(state.textureIndex);
		setVisible(state.visible);
		this.state = state;
		state.setMapElement(this);
	}

	/**
	 * Get the map element state
	 */
	@Override
	public MapElementState getState() {
		return (state);
	}

	/**
	 * Set the texture index for the icon
	 * 
	 * @param textureIndex
	 */
	public void setTexture(int textureIndex) {
		if (this.textureIndex == textureIndex) {
			return;
		}
		this.textureIndex = textureIndex;
		if (nominalTexture[textureIndex] == null) {
			nominalTexture[textureIndex] = ImageUtil.createTexture(Icons.getIconURL(ICON_NAME[textureIndex] + ".png"),
				true);
			highlightTexture[textureIndex] = ImageUtil.createTexture(
				Icons.getIconURL(ICON_NAME[textureIndex] + "-highlight.png"), true);
		}
		setTexture(nominalTexture[textureIndex], highlightTexture[textureIndex]);
	}

	/**
	 * Get the current texture index
	 * 
	 * @return
	 */
	public int getTextureIndex() {
		return (textureIndex);
	}

	/**
	 * Get the point and distance to seek to
	 */
	@Override
	public double getSeekPointAndDistance(Vector3 point) {
		point.set(getTranslation());
		return (getRadius() * 1.5);
	}

	/**
	 * Get the map element type
	 */
	@Override
	public Type getType() {
		return (Type.Placemark);
	}

	@Override
	public Icon getIcon() {
		return (icon);
	}

	/**
	 * Set the defaults
	 * 
	 * @param properties
	 */
	public static void setDefaultsFromProperties(Properties properties) {
		defaultColor = StringUtil.getColorValue(properties, "MapElement.Placemark.defaultColor", defaultColor, false);
		defaultSize = (float) StringUtil.getDoubleValue(properties, "MapElement.Placemark.defaultSize", true,
			defaultSize, false);
		defaultTextureIndex = StringUtil.getIntegerValue(properties, "MapElement.Placemark.defaultTextureIndex", true,
			defaultTextureIndex, false);
		defaultLabelVisible = StringUtil.getBooleanValue(properties, "MapElement.Placemark.defaultLabelVisible",
			defaultLabelVisible, false);
		defaultPinned = StringUtil.getBooleanValue(properties, "MapElement.Placemark.defaultPinned", defaultPinned,
			false);
	}

	/**
	 * Get the defaults
	 * 
	 * @param properties
	 */
	public static void saveDefaultsToProperties(Properties properties) {
		properties.setProperty("MapElement.Placemark.defaultColor", StringUtil.colorToString(defaultColor));
		properties.setProperty("MapElement.Placemark.defaultSize", Double.toString(defaultSize));
		properties.setProperty("MapElement.Placemark.defaultTextureIndex", Integer.toString(defaultTextureIndex));
		properties.setProperty("MapElement.Placemark.defaultLabelVisible", Boolean.toString(defaultLabelVisible));
		properties.setProperty("MapElement.Placemark.defaultPinned", Boolean.toString(defaultPinned));
	}
}
