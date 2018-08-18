function TokenTransactionModel(json) {
    var self = this;
    this.credit = ko.observable(json.credit);
    this.debit = ko.observable(json.debit);
    this.isDebit = ko.computed(function() {
        var isVisible = (self.debit() || 0) > 0;
        return isVisible;
    });
    this.date = ko.observable(json.date);
    this.commit = ko.observable(json.commit);
    this.description = ko.computed(function() {
        var credit = self.credit() || '_';
        var debit = self.debit() || '_';
        return credit + ' ' + debit + ' ' + self.date();
    });
};
