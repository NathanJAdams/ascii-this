function CreditsViewModel(user) {
    var self = this;
    this.user = user;
    this.credits = 12;//user.credits;
    this.creditsTitle = ko.computed(function() {
        return self.credits + " credits";
    });
    this.creditTransactions = ko.observableArray();
    this.creditTransactions.push(new CreditTransactionModel({credit: 1000, debit: 0, date: '2018-08-16_15:45:37 (UTC)', commit: null}));
    this.creditTransactions.push(new CreditTransactionModel({credit: 0, debit: 150, date: '2018-08-09_08:02:19 (UTC)', commit: 'https://www.github.com/commits/fjdsfksfjdskflfjds'}));
}
