/**
 *         __ __ __                        __ 
 * .-----.|__|  |  |--.----.-----.---.-.--|  |
 * |__ --||  |  |    <|   _|  _  |  _  |  _  |
 * |_____||__|__|__|__|__| |_____|___._|_____|
 *
 * implementa la clase padre de todas las figuras que se usan en el programa,
 * esto con el fin de no repetir el mismo codigo que todas comparten
 *
 * @author juan diego patino munoz ; hever barrera batero
 * @version 1
 */
package canvas;

import silkroad.Misc;

public class ShapeCommon
{
    protected SColor  color;
    protected int     pxrow;
    protected int     pxcol;
    protected boolean visibility;

    public ShapeCommon (final SColor color, final int pxrow, final int pxcol)
    {
        this.color      = color;
        this.pxrow      = pxrow;
        this.pxcol      = pxcol;
        this.visibility = false;
    }

    /**
     * actualiza la visibilidad de la figura, hay una pequena optimizacion
     * la cual es terminar la funcion si el estado actual es igual al que
     * se desea estar
     *
     * @param state true si se quiere mostrar, false si no
     */
    public final void changevisibility (final boolean state)
    {
        if (Misc.TESTING) { return; }
        if (state == this.visibility) { return; }
        if (state)
        {
            this.visibility = true;
            this.draw();
            return;
        }
        this.erase();
        this.visibility = false;
    }

    protected void draw ()
    {
    }
    
    /**
     * elimina esta referencia del canvas global, este metodo si puede ser compartido
     * a lo largo de las clases ya que no espcifica una figura
     */
    protected final void erase ()
    {
        if (Misc.TESTING) { return; }
        if (!this.visibility) { return; }
        final SilkRoadCanvas canvas = SilkRoadCanvas.getSilkRoadCanvas();
        canvas.erase(this);
    }
}
