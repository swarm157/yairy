
import ru.swarm.Dairy
import java.awt.Dimension
import java.awt.Toolkit
import java.awt.event.ActionEvent
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.io.*
import javax.swing.*


var toolkit = Toolkit.getDefaultToolkit()
var window = JFrame("Yairy")
var panel = JPanel()
var area = JTextArea()
var scroll = JScrollPane(area)
var next = JButton("next")
var number = JLabel("number")
var previous = JButton("previous")
var width = 600
var height = 800
var xPos = toolkit.screenSize.width/6
var yPos = toolkit.screenSize.height/6
var dairy = Dairy()
var page = 0



fun Dairy(): Dairy {
    val file = File("dairy.save")
    return if (file.exists() && file.isFile) {
        val stream = FileInputStream(file)
        val objectInput = ObjectInputStream(stream)
        objectInput.readObject() as Dairy
    } else {
        ru.swarm.Dairy(ArrayList<String>())
    }
}

class Previous : AbstractAction() {
    override fun actionPerformed(e: ActionEvent?) {
        dairy.pages[page]=area.text
        if (dairy.pages.size==0) page+=1
        page-=1
        number.text = (page+1).toString()
        area.text = dairy.pages[page]
    }

}
class Next : AbstractAction() {
    override fun actionPerformed(e: ActionEvent?) {
        dairy.pages[page]=area.text
        if (page==dairy.pages.size-1) dairy.pages.add("")
        page+=1
        number.text = (page+1).toString()
        area.text = dairy.pages[page]
    }

}

class WindowEvents : WindowListener {
    override fun windowOpened(e: WindowEvent?) {
        save()
    }

    override fun windowClosing(e: WindowEvent?) {
        save()
    }

    override fun windowClosed(e: WindowEvent?) {
        save()
    }

    override fun windowIconified(e: WindowEvent?) {
        save()
    }

    override fun windowDeiconified(e: WindowEvent?) {
        save()
    }

    override fun windowActivated(e: WindowEvent?) {
        save()
    }

    override fun windowDeactivated(e: WindowEvent?) {
        save()
    }

}

fun save() {
    dairy.pages[page]=area.text
    val cleared = ArrayList<String>()
    for (page in dairy.pages) {
        if (!(page == "" || page == null || page.replace(" ", "").replace("\n", "").replace("   ", "")=="")) cleared.add(page)
    }
    val temp = Dairy(cleared)
    val file = File("dairy.save")
    val stream = FileOutputStream(file)
    val out = ObjectOutputStream(stream)
    out.writeObject(temp)
    out.flush()
    out.close()
    stream.flush()
    stream.close()
}

fun main(args: Array<String>) {
    window.setLocation(xPos, yPos)
    window.setSize(width, height)
    window.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    window.add(panel)
    scroll.horizontalScrollBarPolicy = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
    scroll.verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
    scroll.size = Dimension(width-10, height-80)
    scroll.minimumSize = Dimension(width-10, height-80)
    scroll.maximumSize = Dimension(width-10, height-80)
    scroll.preferredSize = Dimension(width-10, height-80)

    panel.add(scroll)
    panel.add(previous)
    panel.add(number)
    panel.add(next)

    if (dairy.pages.size==0) dairy.pages.add("welcome to new dairy!!!")
    page = dairy.pages.size-1
    number.text = (page+1).toString()
    area.text = dairy.pages[page]


    previous.addActionListener(Previous())
    next.addActionListener(Next())

    window.addWindowListener(WindowEvents())
    window.isVisible = true
    window.isResizable = false

}