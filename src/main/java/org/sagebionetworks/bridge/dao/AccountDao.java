package org.sagebionetworks.bridge.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.sagebionetworks.bridge.models.AccountSummarySearch;
import org.sagebionetworks.bridge.models.PagedResourceList;
import org.sagebionetworks.bridge.models.accounts.Account;
import org.sagebionetworks.bridge.models.accounts.AccountId;
import org.sagebionetworks.bridge.models.accounts.AccountSummary;
import org.sagebionetworks.bridge.models.studies.Study;

/**
 * DAO to retrieve personally identifiable account information, including authentication 
 * credentials.
 */
public interface AccountDao {
    
    int MIGRATION_VERSION = 1;
    
    /**
     * Search for all accounts across studies that have the same Synapse user ID in common, 
     * and return a list of the study IDs where these accounts are found.
     * @param synapseUserId
     * @return list of study identifiers
     */
    List<String> getStudyIdsForUser(String synapseUserId);
    
    /**
     * Create an account. If the optional consumer is passed to this method and it throws an 
     * exception, the account will not be persisted (the consumer is executed after the persist 
     * is executed in a transaction, however).
     */
    void createAccount(Study study, Account account, Consumer<Account> afterPersistConsumer);
    
    /**
     * Save account changes. If the optional consumer is passed to this method and 
     * it throws an exception, the account will not be persisted (the consumer is executed after 
     * the persist is executed in a transaction, however).
     */
    void updateAccount(Account account, Consumer<Account> afterPersistConsumer);
    
    /**
     * Get an account in the context of a study by the user's ID, email address, health code,
     * phone number, or Synapse user ID. 
     */
    Optional<Account> getAccount(AccountId accountId);
    
    /**
     * Delete an account along with the authentication credentials.
     */
    void deleteAccount(String userId);
    
    /**
     * Get a page of lightweight account summaries. 
     * @param study
     *      retrieve participants in this study
     * @param search
     *      all the parameters necessary to perform a filtered search of user account summaries, including
     *      paging parameters.
     */
    PagedResourceList<AccountSummary> getPagedAccountSummaries(Study study, AccountSummarySearch search);
}    
