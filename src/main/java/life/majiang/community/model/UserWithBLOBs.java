package life.majiang.community.model;

import java.io.Serializable;

public class UserWithBLOBs extends User implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.ssl_cipher
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private byte[] sslCipher;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.x509_issuer
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private byte[] x509Issuer;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.x509_subject
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private byte[] x509Subject;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.authentication_string
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private String authenticationString;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.User_attributes
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private String userAttributes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.ssl_cipher
     *
     * @return the value of user.ssl_cipher
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public byte[] getSslCipher() {
        return sslCipher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.ssl_cipher
     *
     * @param sslCipher the value for user.ssl_cipher
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public void setSslCipher(byte[] sslCipher) {
        this.sslCipher = sslCipher;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.x509_issuer
     *
     * @return the value of user.x509_issuer
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public byte[] getX509Issuer() {
        return x509Issuer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.x509_issuer
     *
     * @param x509Issuer the value for user.x509_issuer
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public void setX509Issuer(byte[] x509Issuer) {
        this.x509Issuer = x509Issuer;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.x509_subject
     *
     * @return the value of user.x509_subject
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public byte[] getX509Subject() {
        return x509Subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.x509_subject
     *
     * @param x509Subject the value for user.x509_subject
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public void setX509Subject(byte[] x509Subject) {
        this.x509Subject = x509Subject;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.authentication_string
     *
     * @return the value of user.authentication_string
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public String getAuthenticationString() {
        return authenticationString;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.authentication_string
     *
     * @param authenticationString the value for user.authentication_string
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public void setAuthenticationString(String authenticationString) {
        this.authenticationString = authenticationString == null ? null : authenticationString.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.User_attributes
     *
     * @return the value of user.User_attributes
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public String getUserAttributes() {
        return userAttributes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.User_attributes
     *
     * @param userAttributes the value for user.User_attributes
     *
     * @mbg.generated Tue Mar 14 16:26:41 JST 2023
     */
    public void setUserAttributes(String userAttributes) {
        this.userAttributes = userAttributes == null ? null : userAttributes.trim();
    }
}