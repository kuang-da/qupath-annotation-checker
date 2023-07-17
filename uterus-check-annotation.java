import qupath.lib.gui.QuPathGUI
import qupath.lib.io.PathIO
import qupath.lib.objects.classes.PathClass
import static qupath.lib.gui.scripting.QPEx.*

// Define annotation name list
def annotationNameList = [
    "Null",
    "endometrium",
    "glands",
    "blood vessels",
    "luminum epithelium",
    "glandular epithelium",
    "myometrial-endometrial interface",
    "myometrium"]

// Define a counter for each annotation
def annotationCounter = [0, 0, 0, 0, 0, 0, 0, 0]
// Get the current project open in QuPath
def project = getProject()
if (project == null) {
    print 'No project open!'
    return
}

print("----------")
for (entry in project.getImageList()) {
    def imageData = entry.readImageData()
    def hierarchy = imageData.getHierarchy()
    def annotations = hierarchy.getAnnotationObjects()
    print entry.getImageName() + '\t' + annotations.size()
    print entry.getImageName() + '\t' + annotations
    // count the number of each annotation, which can be a null object
    for (annotation in annotations) {
        if (annotation.getPathClass() == null) {
            annotationCounter[0]++
        } else {
            for (int i = 1; i < annotationNameList.size(); i++) {
                if (annotation.getPathClass().getName() == annotationNameList[i]) {
                    annotationCounter[i]++
                }
        }
        }
    }
}
print("----------")
print("# of images:")
print(project.getImageList().size())
print("# of Annotations:") 
print(annotationCounter.sum())
print("# of Annotations classes:") 
print(annotationNameList.size())
print(annotationCounter)