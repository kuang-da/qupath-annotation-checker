import qupath.lib.gui.QuPathGUI
import qupath.lib.io.PathIO
import qupath.lib.objects.classes.PathClass
import static qupath.lib.gui.scripting.QPEx.*
import java.nio.file.Paths

// Exports annotations to a CSV file
// The CSV file will have the following columns:
// 1. Annotation name
// 2. Image name
// 3. Annotation class

// Get the current project open in QuPath
def project = getProject()
if (project == null) {
    print 'No project open!'
    return
}

// Iterative over all images in the project and export annotations
// Export annotations to CSV file
// images are in images/ subfolder, annotations are in annotations/ subfolder
for (entry in project.getImageList()) {
    def imageData = entry.readImageData()
    def server = imageData.getServer()
    String path = server.getPath()
    String imageName = path[path.lastIndexOf('/')+1..-1]
    print imageName
    String imagePath = Paths.get(path[path.lastIndexOf(':')+1..-1]).getParent().toString()
    String csvPath = imagePath.replace('images', 'annotations')
    String csvName = imageName + '.csv'
    String csvFullPath = csvPath + '/' + csvName
    // Create CSV file
    File csvFile = new File(csvFullPath)
    csvFile.createNewFile()
    // Write CSV file
    FileWriter csvWriter = new FileWriter(csvFile)
    csvWriter.append("Annotation,Image,Class\n")
    def hierarchy = imageData.getHierarchy()
    def annotations = hierarchy.getAnnotationObjects()
    for (annotation in annotations) {
        def annotationName = annotation.getName()
        
        String annotationClass;
        if (annotation.getPathClass() == null) {
            annotationClass = 'Null'
        }else{
            annotationClass = annotation.getPathClass().getName()
        }
        
        print annotationName + ',' + imageName + ',' + annotationClass
        csvWriter.append(annotationName + ',' + imageName + ',' + annotationClass + '\n')
    }
    csvWriter.flush()
    csvWriter.close()
}
print("----------")