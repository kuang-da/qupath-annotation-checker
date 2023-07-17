import qupath.lib.gui.QuPathGUI
import qupath.lib.io.PathIO
import qupath.lib.objects.classes.PathClass
import static qupath.lib.gui.scripting.QPEx.*

// Define annotation name list
def annotationNameList = [
    "Null",
    "ovarian surface epithelium",
    "preantral follicle",
    "antral follicle",
    "postovulatory follicle",
    "corpus luteum",
    "oocyte",
    "atretic follicle",
    "hemorrhagic anovulatory follicle",
    "luteinized unruptured follicle",
    "primary ovarian follicle",
    "primordial ovarian follicle",
    "transitional primary ovarian follicle",
    "transitional primordial ovarian follicle",
    "secondary ovarian follicle",
    "multilayer ovarian follicle",
    "early antral follicle",
    "pre-selection antral follicle",
    "selection antral follicle",
    "dominance antral follicle",
    "ovulatory antral follicle",
    "stroma",
    "blood vessels",
    "Cortex",
    "Medulla",
    "corpora albicans"]

// Define a counter for each annotation
def annotationCounter = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
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