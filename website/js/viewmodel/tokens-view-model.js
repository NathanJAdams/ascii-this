function TokensViewModel(user) {
    var self = this;
    this.user = user;
    this.tokens = 12;//user.tokens;
    this.tokensTitle = ko.computed(function() {
        return self.tokens + " tokens";
    });
    this.tokenTransactions = ko.observableArray();
    this.tokenTransactions.push(new TokenTransactionModel({credit: 1000, debit: 0, date: '2018-08-16_15:45:37 (UTC)', commit: null}));
    this.tokenTransactions.push(new TokenTransactionModel({credit: 0, debit: 150, date: '2018-08-09_08:02:19 (UTC)', commit: 'https://www.github.com/commits/fjdsfksfjdskflfjds'}));
}
