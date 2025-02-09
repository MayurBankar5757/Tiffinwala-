using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace TWservice2.Models
{
    public partial class p10_tiffinwalaContext : DbContext
    {
        public p10_tiffinwalaContext()
        {
        }

        public p10_tiffinwalaContext(DbContextOptions<p10_tiffinwalaContext> options)
            : base(options)
        {
        }

        public virtual DbSet<CustomerOrderLog> CustomerOrderLogs { get; set; } = null!;
        public virtual DbSet<CustomerSubscribedPlan> CustomerSubscribedPlans { get; set; } = null!;
        public virtual DbSet<CustomerSubscriptionsJunction> CustomerSubscriptionsJunctions { get; set; } = null!;
        public virtual DbSet<Feedback> Feedbacks { get; set; } = null!;
        public virtual DbSet<Payment> Payments { get; set; } = null!;
        public virtual DbSet<Role> Roles { get; set; } = null!;
        public virtual DbSet<Tiffin> Tiffins { get; set; } = null!;
        public virtual DbSet<User> Users { get; set; } = null!;
        public virtual DbSet<Vendor> Vendors { get; set; } = null!;
        public virtual DbSet<VendorSubscriptionPlan> VendorSubscriptionPlans { get; set; } = null!;

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
                optionsBuilder.UseMySql("server=localhost;port=3306;user=root;password=root;database=p10_tiffinwala", Microsoft.EntityFrameworkCore.ServerVersion.Parse("8.2.0-mysql"));
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.UseCollation("utf8mb4_0900_ai_ci")
                .HasCharSet("utf8mb4");

            modelBuilder.Entity<CustomerOrderLog>(entity =>
            {
                entity.HasKey(e => e.OrderId)
                    .HasName("PRIMARY");

                entity.ToTable("customer_order_log");

                entity.HasIndex(e => e.Uid, "Uid");

                entity.Property(e => e.OrderId).HasColumnName("Order_id");

                entity.Property(e => e.OrderDate)
                    .HasMaxLength(6)
                    .HasColumnName("order_date");

                entity.Property(e => e.Quantity).HasColumnName("quantity");

                entity.HasOne(d => d.UidNavigation)
                    .WithMany(p => p.CustomerOrderLogs)
                    .HasForeignKey(d => d.Uid)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_order_log_ibfk_1");
            });

            modelBuilder.Entity<CustomerSubscribedPlan>(entity =>
            {
                entity.HasKey(e => e.CustomerPlanId)
                    .HasName("PRIMARY");

                entity.ToTable("customer_subscribed_plans");

                entity.HasIndex(e => e.Uid, "Uid");

                entity.HasIndex(e => e.VSubscriptionId, "v_subscription_id");

                entity.Property(e => e.CustomerPlanId).HasColumnName("Customer_plan_id");

                entity.Property(e => e.EndDate).HasColumnName("end_date");

                entity.Property(e => e.OrderedDate).HasColumnName("ordered_date");

                entity.Property(e => e.StartDate).HasColumnName("start_date");

                entity.Property(e => e.VSubscriptionId).HasColumnName("v_subscription_id");

                entity.HasOne(d => d.UidNavigation)
                    .WithMany(p => p.CustomerSubscribedPlans)
                    .HasForeignKey(d => d.Uid)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_subscribed_plans_ibfk_1");

                entity.HasOne(d => d.VSubscription)
                    .WithMany(p => p.CustomerSubscribedPlans)
                    .HasForeignKey(d => d.VSubscriptionId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_subscribed_plans_ibfk_2");
            });

            modelBuilder.Entity<CustomerSubscriptionsJunction>(entity =>
            {
                entity.HasKey(e => new { e.CustomerPlanId, e.Uid, e.PlanId })
                    .HasName("PRIMARY")
                    .HasAnnotation("MySql:IndexPrefixLength", new[] { 0, 0, 0 });

                entity.ToTable("customer_subscriptions_junction");

                entity.HasIndex(e => e.PlanId, "Plan_id");

                entity.HasIndex(e => e.Uid, "Uid");

                entity.Property(e => e.CustomerPlanId).HasColumnName("Customer_plan_id");

                entity.Property(e => e.PlanId).HasColumnName("Plan_id");

                entity.HasOne(d => d.CustomerPlan)
                    .WithMany(p => p.CustomerSubscriptionsJunctions)
                    .HasForeignKey(d => d.CustomerPlanId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_subscriptions_junction_ibfk_1");

                entity.HasOne(d => d.Plan)
                    .WithMany(p => p.CustomerSubscriptionsJunctions)
                    .HasForeignKey(d => d.PlanId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_subscriptions_junction_ibfk_3");

                entity.HasOne(d => d.UidNavigation)
                    .WithMany(p => p.CustomerSubscriptionsJunctions)
                    .HasForeignKey(d => d.Uid)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("customer_subscriptions_junction_ibfk_2");
            });

            modelBuilder.Entity<Feedback>(entity =>
            {
                entity.ToTable("feedback");

                entity.HasIndex(e => e.CustomerPlanId, "customer_plan_id");

                entity.HasIndex(e => e.Uid, "uid");

                entity.HasIndex(e => e.VSubscriptionId, "v_subscription_id");

                entity.Property(e => e.FeedbackId).HasColumnName("feedback_id");

                entity.Property(e => e.CustomerPlanId).HasColumnName("customer_plan_id");

                entity.Property(e => e.FeedbackDate)
                    .HasColumnName("feedback_date")
                    .HasDefaultValueSql("'2025-01-01'");

                entity.Property(e => e.FeedbackText)
                    .HasMaxLength(500)
                    .HasColumnName("feedback_text");

                entity.Property(e => e.Uid).HasColumnName("uid");

                entity.Property(e => e.VSubscriptionId).HasColumnName("v_subscription_id");

                entity.HasOne(d => d.CustomerPlan)
                    .WithMany(p => p.Feedbacks)
                    .HasForeignKey(d => d.CustomerPlanId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("feedback_ibfk_1");

                entity.HasOne(d => d.UidNavigation)
                    .WithMany(p => p.Feedbacks)
                    .HasForeignKey(d => d.Uid)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("feedback_ibfk_2");

                entity.HasOne(d => d.VSubscription)
                    .WithMany(p => p.Feedbacks)
                    .HasForeignKey(d => d.VSubscriptionId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("feedback_ibfk_3");
            });

            modelBuilder.Entity<Payment>(entity =>
            {
                entity.HasKey(e => e.Pid)
                    .HasName("PRIMARY");

                entity.ToTable("payment");

                entity.HasIndex(e => e.OrderId, "Order_id");

                entity.HasIndex(e => e.RazorPayId, "RazorPay_id")
                    .IsUnique();

                entity.Property(e => e.Amount).HasPrecision(10, 2);

                entity.Property(e => e.OrderId).HasColumnName("Order_id");

                entity.Property(e => e.PaymentDate).HasColumnName("Payment_date");

                entity.Property(e => e.RazorPayId).HasColumnName("RazorPay_id");

                entity.HasOne(d => d.Order)
                    .WithMany(p => p.Payments)
                    .HasForeignKey(d => d.OrderId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("payment_ibfk_1");
            });

            modelBuilder.Entity<Role>(entity =>
            {
                entity.ToTable("role");

                entity.HasIndex(e => e.RoleName, "Role_Name")
                    .IsUnique();

                entity.Property(e => e.RoleId).HasColumnName("Role_Id");

                entity.Property(e => e.RoleName)
                    .HasMaxLength(50)
                    .HasColumnName("role_name");
            });

            modelBuilder.Entity<Tiffin>(entity =>
            {
                entity.ToTable("tiffin");

                entity.HasIndex(e => e.VSubscriptionId, "V_Subscription_id");

                entity.Property(e => e.TiffinId).HasColumnName("Tiffin_id");

                entity.Property(e => e.Day)
                    .HasMaxLength(255)
                    .HasColumnName("day");

                entity.Property(e => e.Description)
                    .HasMaxLength(255)
                    .HasColumnName("description");

                entity.Property(e => e.FoodType)
                    .HasMaxLength(255)
                    .HasColumnName("food_type");

                entity.Property(e => e.TiffinName)
                    .HasMaxLength(255)
                    .HasColumnName("Tiffin_name");

                entity.Property(e => e.VSubscriptionId).HasColumnName("V_Subscription_id");

                entity.HasOne(d => d.VSubscription)
                    .WithMany(p => p.Tiffins)
                    .HasForeignKey(d => d.VSubscriptionId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("tiffin_ibfk_1");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.HasKey(e => e.Uid)
                    .HasName("PRIMARY");

                entity.ToTable("user");

                entity.HasIndex(e => e.Email, "email")
                    .IsUnique();

                entity.HasIndex(e => e.Rid, "role_id");

                entity.Property(e => e.Area)
                    .HasMaxLength(255)
                    .HasColumnName("area");

                entity.Property(e => e.City)
                    .HasMaxLength(255)
                    .HasColumnName("city");

                entity.Property(e => e.Contact)
                    .HasMaxLength(20)
                    .HasColumnName("contact");

                entity.Property(e => e.Email)
                    .HasMaxLength(70)
                    .HasColumnName("email");

                entity.Property(e => e.Fname)
                    .HasMaxLength(50)
                    .HasColumnName("fname");

                entity.Property(e => e.Lname)
                    .HasMaxLength(50)
                    .HasColumnName("lname");

                entity.Property(e => e.Password)
                    .HasMaxLength(30)
                    .HasColumnName("password");

                entity.Property(e => e.Pincode)
                    .HasMaxLength(255)
                    .HasColumnName("pincode");

                entity.Property(e => e.State)
                    .HasMaxLength(255)
                    .HasColumnName("state");

                entity.HasOne(d => d.RidNavigation)
                    .WithMany(p => p.Users)
                    .HasForeignKey(d => d.Rid)
                    .HasConstraintName("user_ibfk_1");
            });

            modelBuilder.Entity<Vendor>(entity =>
            {
                entity.ToTable("vendor");

                entity.HasIndex(e => e.Uid, "Uid");

                entity.Property(e => e.VendorId).HasColumnName("Vendor_id");

                entity.Property(e => e.AdharNo)
                    .HasMaxLength(255)
                    .HasColumnName("adhar_no");

                entity.Property(e => e.FoodLicenceNo)
                    .HasMaxLength(255)
                    .HasColumnName("food_licence_no");

                entity.Property(e => e.IsVerified).HasColumnName("Is_verified");

                entity.HasOne(d => d.UidNavigation)
                    .WithMany(p => p.Vendors)
                    .HasForeignKey(d => d.Uid)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("vendor_ibfk_1");
            });

            modelBuilder.Entity<VendorSubscriptionPlan>(entity =>
            {
                entity.HasKey(e => e.PlanId)
                    .HasName("PRIMARY");

                entity.ToTable("vendor_subscription_plan");

                entity.HasIndex(e => e.VendorId, "Vendor_id");

                entity.Property(e => e.PlanId).HasColumnName("Plan_id");

                entity.Property(e => e.Description).HasMaxLength(255);

                entity.Property(e => e.Duration)
                    .HasColumnType("enum('SEVEN_DAYS','THIRTY_DAYS')")
                    .HasColumnName("duration");

                entity.Property(e => e.IsAvailable)
                    .IsRequired()
                    .HasColumnName("is_available")
                    .HasDefaultValueSql("'1'");

                entity.Property(e => e.Name).HasMaxLength(255);

                entity.Property(e => e.VendorId).HasColumnName("Vendor_id");

                entity.HasOne(d => d.Vendor)
                    .WithMany(p => p.VendorSubscriptionPlans)
                    .HasForeignKey(d => d.VendorId)
                    .HasConstraintName("vendor_subscription_plan_ibfk_1");
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
