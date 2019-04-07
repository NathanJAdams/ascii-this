function DiffViewModel() {
    var self = this;
    this.fileDiffs = ko.observableArray();
    this.appendFilePartElements = function(dataArray, commonArray, className, elementArray) {
        var commonIndex = 0;
        for (i=0; i<dataArray.length; i++) {
            var element = dataArray[i];
            var filePartElement = new FilePartElementDiffModel();
            if (element === null) {
                filePartElement.diffText(commonArray[commonIndex] + '\n');
                filePartElement.className('diff-common');
                commonIndex++;
            } else {
                filePartElement.diffText(element + '\n');
                filePartElement.className(className);
            }
            elementArray.push(filePartElement);
        }
    };
    this.update = function(json) {
        self.fileDiffs([]);
        var dataDiff = json.diff;
        for (var i=0; i<dataDiff.fileDiffs.length; i++) {
            var dataFileDiff = dataDiff.fileDiffs[i];
            var fileDiff = new FileDiffModel();
            fileDiff.fileName(dataFileDiff.fileNameA);
            var dataFilePartDiffs = dataFileDiff.filePartDiffs;
            for (var j=0; j<dataFilePartDiffs.length; j++) {
                var dataFilePartDiff = dataFilePartDiffs[j];
                var filePartDiff = new FilePartDiffModel();
                var common = dataFilePartDiff.common;
                self.appendFilePartElements(dataFilePartDiff.a, common, 'diff-removed', filePartDiff.a);
                self.appendFilePartElements(dataFilePartDiff.b, common, 'diff-added', filePartDiff.b);
                fileDiff.fileParts.push(filePartDiff);
            }
            self.fileDiffs.push(fileDiff);
        }
    };
}
