import java.awt.event.*;
/**
 * <<Class summary>>
 *
 * @author Mohammad Khatib &lt;&gt;
 * @version $Rev$
 */
public interface PuzzleListener {
    public void stateChanged(ActionEvent e);	
	public void goalFound(ActionEvent e);
}
