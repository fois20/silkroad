/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * define gran variedad de colores para la simulacion; se definen
 * 17 colores para techos, 17 colores para fachadas, 17 colores
 * para robots, el color de la arena y el color del terreno por donde
 * los robots caminan
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package canvas;

import java.awt.Color;

public enum SColor
{
	sand (new Color(227, 212, 168)),
	road (new Color(139, 125, 107)),

	normalFC1  (new Color(0, 70, 160)),
	normalRC1  (new Color(120, 180, 255)),

	normalFC2  (new Color(180, 30, 30)),
	normalRC2  (new Color(255, 140, 140)),

	normalFC3  (new Color(255, 100, 0)),
	normalRC3  (new Color(255, 190, 130)),

	normalFC4  (new Color(100, 0, 150)),
	normalRC4  (new Color(200, 150, 230)),

	normalFC5  (new Color(220, 40, 120)),
	normalRC5  (new Color(255, 170, 210)),

	normalFC6  (new Color(100, 60, 30)),
	normalRC6  (new Color(200, 160, 120)),

	normalFC7  (new Color(200, 160, 0)),
	normalRC7  (new Color(255, 240, 130)),

	normalFC8  (new Color(30, 120, 30)),
	normalRC8  (new Color(160, 230, 160)),

	normalFC9  (new Color(0, 150, 160)),
	normalRC9  (new Color(140, 230, 240)),

	normalFC10 (new Color(230, 210, 170)),
	normalRC10 (new Color(255, 245, 220)),

	normalFC11 (new Color(120, 0, 80)),
	normalRC11 (new Color(230, 150, 200)),

	normalFC12 (new Color(0, 100, 100)),
	normalRC12 (new Color(170, 220, 220)),

	normalFC13 (new Color(180, 90, 10)),
	normalRC13 (new Color(250, 200, 120)),

	normalFC14 (new Color(90, 90, 180)),
	normalRC14 (new Color(190, 190, 255)),

	normalFC15 (new Color(40, 40, 40)),
	normalRC15 (new Color(160, 160, 160)),

	normalFC16 (new Color(100, 40, 60)),
	normalRC16 (new Color(210, 140, 160)),

	normalFC17 (new Color(80, 130, 60)),
	normalRC17 (new Color(180, 230, 160)),

	normalRobotC1  (new Color(180, 180, 170)),
	normalRobotC2  (new Color(160, 150, 140)),
	normalRobotC3  (new Color(200, 190, 180)),
	normalRobotC4  (new Color(150, 150, 150)),
	normalRobotC5  (new Color(140, 130, 120)),
	normalRobotC6  (new Color(110, 100, 90 )),
	normalRobotC7  (new Color(170, 160, 140)),
	normalRobotC8  (new Color(130, 110, 90 )),
	normalRobotC9  (new Color(190, 180, 160)),
	normalRobotC10 (new Color(100, 110, 100)),
	normalRobotC11 (new Color(120, 130, 120)),
	normalRobotC12 (new Color(110, 120, 130)),
	normalRobotC13 (new Color(130, 140, 150)),
	normalRobotC14 (new Color(160, 165, 170)),
	normalRobotC15 (new Color(120, 100, 80 )),
	normalRobotC16 (new Color(100, 90,  70 )),
	normalRobotC17 (new Color(110, 110, 110));

	private final Color self;

	SColor (final Color color) { this.self = color; }
	public Color getcolor ()   { return this.self; }
}
